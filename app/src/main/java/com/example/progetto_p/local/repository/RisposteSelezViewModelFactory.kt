package com.example.progetto_p.local.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.RisposteSelezViewModel

class RisposteSelezViewModelFactory(
    private val repository: RisposteSelezRepository,
    private val sessionManager: SessionManager
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RisposteSelezViewModel::class.java)){
            return RisposteSelezViewModel(sessionManager,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}