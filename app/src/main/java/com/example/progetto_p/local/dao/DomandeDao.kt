package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import kotlinx.coroutines.flow.Flow

@Dao
interface DomandeDao {

    @Query("SELECT * FROM domande WHERE questionarioId = :questionarioId ORDER BY ordine ASC")
    fun getDomandeById(questionarioId: Int): Flow<List<Domande>>

    @Query("SELECT * FROM opzioniRisposta WHERE domandaId = :domandaId ORDER BY ordine ASC")
    fun getRisposte(domandaId: Int): Flow<List<OpzioniRisposta>>

}