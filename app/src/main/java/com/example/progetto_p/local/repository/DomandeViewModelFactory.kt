package com.example.progetto_p.local.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.progetto_p.ui.DomandeViewModel
import com.example.progetto_p.ui.UtentiViewModel


//permette ad android di spiegare come si crea la viewModel con dentro la repository
class DomandeViewModelFactory (
    private val repository: DomandeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()

        if (modelClass.isAssignableFrom(DomandeViewModel::class.java)) {
            return DomandeViewModel(
                savedStateHandle = savedStateHandle,
                repository = repository
            ) as T
        }
        throw IllegalArgumentException("Classe ViewModel sconosciuta")
    }
}
