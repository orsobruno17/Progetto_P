package com.example.progetto_p.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import com.example.progetto_p.local.repository.DomandeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DomandeViewModel (
    savedStateHandle: SavedStateHandle,
    private val repository: DomandeRepository
): ViewModel(){


    //estraggo l'id dall'argomento della navigazione
    private val questionarioId: Int = checkNotNull(savedStateHandle.get<Int>("questionarioId"))


    val domande: StateFlow<List<Domande>> = repository
        .getDomandeById(questionarioId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun risposte(domandaId: Int): Flow<List<OpzioniRisposta>> {
        return repository.getRisposte(domandaId)
    }

}