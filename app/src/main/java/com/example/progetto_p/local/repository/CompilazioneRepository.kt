package com.example.progetto_p.local.repository

import com.example.progetto_p.local.dao.CompilazioneDao
import com.example.progetto_p.local.entity.CompilazioneUtente
import kotlinx.coroutines.flow.Flow

class CompilazioneRepository(
    private val dao: CompilazioneDao
) {
    suspend fun inserisciCompil(compilazione: CompilazioneUtente){
        dao.complUtente(compilazione)
    }

    fun getCompl(utenteId: String, questionarioId: Int): Flow<Int> {
        return dao.getCompl(utenteId, questionarioId)
    }
}