package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.progetto_p.local.entity.CompilazioneUtente
import kotlinx.coroutines.flow.Flow

@Dao
interface CompilazioneDao {
    @Insert
    suspend fun complUtente(compilazione: CompilazioneUtente)

    @Query("SELECT id FROM compilazioneUtente WHERE utenteId = :utenteId AND questionarioId = :questionarioId ORDER BY id DESC LIMIT 1")
    fun getCompl(utenteId: String, questionarioId: Int): Flow<Int>
}