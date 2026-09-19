package com.example.progetto_p.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto_p.local.dao.QuestionariDao
import com.example.progetto_p.local.dao.UtenteDao
import com.example.progetto_p.local.entity.CompilazioneUtente
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.local.entity.RispostaSelezionata
import com.example.progetto_p.local.entity.Utenti
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.java

@Database(
    entities = [Utenti::class,
        RispostaSelezionata::class,
        Questionari::class,
        OpzioniRisposta::class,
        Domande::class,
        CompilazioneUtente::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun utenteDao(): UtenteDao
    abstract fun questionarioDao(): QuestionariDao

    companion object {
        @Volatile
        private var INSTANCE: com.example.progetto_p.local.database.AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): com.example.progetto_p.local.database.AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Room.databaseBuilder crea oppure ottiene il DB
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.progetto_p.local.database.AppDatabase::class.java,
                    "utenti_database"
                )
                .fallbackToDestructiveMigration() //permette di pulire il db se cambia lo schema durante lo sviluppo
                .addCallback(AppDatabaseCallback(scope))
                .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase){
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO){
                    populateDatabase(database.questionarioDao())
                }

            }?: run {
                // Se INSTANCE è ancora null, usiamo uno scope dedicato per accedere al DB creato
                scope.launch(Dispatchers.IO) {
                    INSTANCE?.questionarioDao()?.let { populateDatabase(it) }
                }
            }
        }

        suspend fun populateDatabase(dao: QuestionariDao){
            val questionarioIniziale = listOf(
                Questionari(
                id = "DLQI",
                titolo = "DLQI",
                descrizione = "Il DLQI, Dermatology Life Quality Index, è progettato per misurare la qualità della vita correlata alla salute dei pazienti adulti affetti da una malattia della pelle.",
                fasciaEta = "maggiore o uguale a 16 anni",
                tempoCompletamento = "Il tempo medio di completamento del DLQI è di due minuti. Di solito non è necessario alcun aiuto.",
                frequenzaUso = "E' necessario attendere almeno sette giorni tra un utilizzo e l'altro. Un utilizzo troppo frequente non è raccomandato, in quanto potreste ricordare le risposte precedenti ed esserne influenzati, diventando meno precisi nel rispondere.",
                ),
                Questionari(
                id = "HADS",
                titolo = "HADS",
                descrizione = "L'Hospital Anxiety and Depression Scale o HADS è’ un semplice questionario autosomministrato per stabilire la presenza e la severità dell’ansia e della depressione simultaneamente",
                fasciaEta = "Consigliabile tra i 16 e i 65 anni",
                tempoCompletamento = "Circa 5 minuti",
                frequenzaUso = null
            ),
                Questionari(
                    id = "WHO-5",
                    titolo = "WHO-5",
                    descrizione = "Il WHO-5 è uno strumento di autovalutazione per la misurazione del benessere mentale. Consiste in cinque affermazioni relative alle ultime due settimane",
                    fasciaEta = null,
                    tempoCompletamento = "Circa 2 minuti",
                    frequenzaUso = null
                )
            )
            questionarioIniziale.forEach { questionario ->
                dao.insert(questionario)
            }

        }
    }
}