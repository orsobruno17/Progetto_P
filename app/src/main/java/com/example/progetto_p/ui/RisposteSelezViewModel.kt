package com.example.progetto_p.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.local.entity.RispostaSelezionata
import com.example.progetto_p.local.repository.RisposteSelezRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RisposteSelezViewModel(
    private val sessionManager: SessionManager,
    private val repository: RisposteSelezRepository
): ViewModel(){

    val utenteLog: StateFlow<String?> = sessionManager.codiceFiscaleFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
    fun salvaRispostaCompilata(compilazioneId: Int, domandaId: Int, punteggio: Int){
            viewModelScope.launch {
                val rispostaCompil = RispostaSelezionata(
                    compilazioneId = compilazioneId,
                    domandaId = domandaId,
                    opzioneSelezionataId = punteggio
                )
                repository.salvaRisposta(rispostaCompil)
            }
    }

}