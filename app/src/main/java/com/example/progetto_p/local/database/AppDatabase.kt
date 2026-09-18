package com.example.progetto_p.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progetto_p.local.dao.UtenteDao
import com.example.progetto_p.local.entity.Utenti
import kotlin.jvm.java

@Database(
    entities = [Utenti::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun utenteDao(): UtenteDao

    companion object {
        @Volatile
        private var INSTANCE: com.example.progetto_p.local.database.AppDatabase? = null

        fun getDatabase(context: Context): com.example.progetto_p.local.database.AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Room.databaseBuilder crea oppure ottiene il DB
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.progetto_p.local.database.AppDatabase::class.java,
                    "utenti_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}