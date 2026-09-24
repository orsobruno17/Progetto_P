package com.example.progetto_p.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_p.local.entity.CompilazioneUtente
import com.example.progetto_p.local.repository.CompilazioneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CompilazioneViewModel(
    private val repository: CompilazioneRepository
): ViewModel() {
    fun inserisciCompil(utenteId: String, questionarioId: Int){
        viewModelScope.launch {
            val nuovaCompil = CompilazioneUtente(
                utenteId = utenteId,
                questionarioId = questionarioId,
                punteggioTot = null
            )
            repository.inserisciCompil(nuovaCompil )
        }
    }

    fun getCompl(utenteId: String, questionarioId: Int): Flow<Int> {
           return repository.getCompl(
                utenteId,
                questionarioId
            )

    }
}