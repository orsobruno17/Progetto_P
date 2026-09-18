package com.example.progetto_p.local.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.progetto_p.ui.UtentiViewModel

class UtentiViewModelFactory(
        private val repository: UtentiRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UtentiViewModel(repository) as T
        }
}
