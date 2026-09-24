package com.example.progetto_p.local.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.ui.CompilazioneViewModel
import com.example.progetto_p.ui.RisposteSelezViewModel

class CompilazioneViewModelFactory (
    private val repository: CompilazioneRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CompilazioneViewModel::class.java)){
            return CompilazioneViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}