package com.example.progetto_p.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_p.local.dao.QuestionariDao
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.local.repository.QuestionariRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionariViewModel (
    private val repository: QuestionariRepository
) : ViewModel() {
    val items: StateFlow<List<Questionari>> = repository.items
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}