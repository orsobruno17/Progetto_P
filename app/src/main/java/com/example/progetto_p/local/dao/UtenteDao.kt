package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.progetto_p.local.entity.Utenti
import kotlinx.coroutines.flow.Flow

@Dao
interface UtenteDao {
    @Query("SELECT * FROM dati_utente")
    fun getAllItems(): Flow<List<Utenti>>

    @Insert
    suspend fun insertItem(item: Utenti)

    @Update
    suspend fun updateItem(item: Utenti)

    @Delete
    suspend fun deleteItem(item: Utenti)

    @Query("SELECT * FROM dati_utente WHERE email = :email AND password = :password")
    suspend fun getItemByEmail(email: String, password: String): Utenti?
}