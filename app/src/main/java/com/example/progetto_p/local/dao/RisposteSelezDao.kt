package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progetto_p.local.entity.RispostaSelezionata
import kotlinx.coroutines.flow.Flow

@Dao
interface RisposteSelezDao {
    //onConflict = OnConflictStrategy.REPLACE serve per sovrascrivere la risposta se l'utente la cambia
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvaRisposta(risposta: RispostaSelezionata)

}