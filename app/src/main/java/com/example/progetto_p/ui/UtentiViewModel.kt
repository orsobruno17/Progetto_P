package com.example.progetto_p.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_p.local.entity.Utenti
import com.example.progetto_p.local.repository.UtentiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UtentiViewModel(
private val repository: UtentiRepository
) : ViewModel() {

    val items: Flow<List<Utenti>> = repository.items

    fun PasswordValida(password: String): Boolean{
        val num = password.any{it.isDigit()}
        val carattere = password.any{ !it.isLetterOrDigit() }

        return num && carattere
    }
    fun registrazione(codiceFiscale : String, nome: String, cognome : String, email : String, password: String, onResult:(Boolean) -> Unit) {
        val codiceFiscale = codiceFiscale.trim()
        val nome = nome.trim()
        val cognome = cognome.trim()
        val email = email.trim()
        val password = password.trim()

        viewModelScope.launch {
            if(!PasswordValida(password)){
                Log.d("LOGIN_DEBUG", "La password non contiene almeno un numero e carattere speciale")
                onResult(false)
            }else{
                repository.addItem(codiceFiscale, nome, cognome, email, password)
                onResult(true)
            }
        }
    }

    fun login(email: String, password: String, onResult:(Boolean) -> Unit){
        val email = email.trim()
        val password = password.trim()

        viewModelScope.launch {
            val user = repository.getItemByEmail( email, password)
            if(user != null){
                Log.d("LOGIN_DEBUG", "Utente trovato: Nome = ${user.nome}, Cognome = ${user.cognome}, Email = ${user.email}")
                onResult(true)
            }else{
                Log.d("LOGIN_DEBUG", "Nessun utente trovato con queste credenziali")
                onResult(false)
            }
        }
    }

}