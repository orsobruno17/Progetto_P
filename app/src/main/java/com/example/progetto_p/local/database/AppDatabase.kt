package com.example.progetto_p.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progetto_p.local.dao.CompilazioneDao
import com.example.progetto_p.local.dao.DomandeDao
import com.example.progetto_p.local.dao.QuestionariDao
import com.example.progetto_p.local.dao.RisposteSelezDao
import com.example.progetto_p.local.dao.UtenteDao
import com.example.progetto_p.local.entity.CompilazioneUtente
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.local.entity.RispostaSelezionata
import com.example.progetto_p.local.entity.Risultati
import com.example.progetto_p.local.entity.Utenti

@Database(
    entities = [
        Utenti::class,
        RispostaSelezionata::class,
        Questionari::class,
        OpzioniRisposta::class,
        Domande::class,
        CompilazioneUtente::class,
        Risultati::class
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun utenteDao(): UtenteDao
    abstract fun questionarioDao(): QuestionariDao
    abstract fun domandeDao(): DomandeDao
    abstract fun risposteSelezionataDao(): RisposteSelezDao

    abstract fun compilazioneDao(): CompilazioneDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "utenti_database"
                )
                    .createFromAsset("database/questionari.db")
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}