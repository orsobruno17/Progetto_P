package com.example.progetto_p.local.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.local.entity.Utenti
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.UtentiViewModel
import kotlinx.coroutines.flow.Flow

class QuestionariViewModelFactory(
    private val repository: QuestionariRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuestionariViewModel::class.java)){
            return QuestionariViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}