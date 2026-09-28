package com.example.progetto_p.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.local.entity.Utenti
import com.example.progetto_p.local.repository.UtentiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.security.MessageDigest

class UtentiViewModel(
private val repository: UtentiRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    var codiceFiscale by mutableStateOf("")
    var nome by mutableStateOf("")
    var cognome by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")


    fun logout(){
        viewModelScope.launch {
            sessionManager.cancellaSessione()
        }
    }

    fun PasswordValida(password: String): Boolean{
        val num = password.any{it.isDigit()}
        val carattere = password.any{ !it.isLetterOrDigit() }

        return num && carattere
    }

    fun CFValido(codiceFiscale: String): Boolean{
        val cf = codiceFiscale.uppercase().trim()

        val regex = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$".toRegex()
        if (!regex.matches(cf)){
            return false}
        return true
    }
    fun registrazione(codiceFiscale : String, nome: String, cognome : String, email : String, password: String, onResult:(esito: Boolean, erroreCF: Boolean, errorePassword: Boolean) -> Unit) {
        val codiceFiscale = codiceFiscale.trim()
        val nome = nome.trim()
        val cognome = cognome.trim()
        val email = email.trim()
        val password = password.trim()

        val cf = CFValido(codiceFiscale)
        val passw = PasswordValida(password)


        if (!cf || !passw) {
            onResult(false, !cf, !passw)
            return
        }
        viewModelScope.launch {
                val hash = password.toSHA256()
                repository.addItem(codiceFiscale, nome, cognome, email, hash)
                sessionManager.salvaSessione(codiceFiscale)
                onResult(true, false, false)
        }
    }

    fun login(email: String, password: String, onResult:(Boolean) -> Unit){
        val email = email.trim()
        val password = password.trim()

        viewModelScope.launch {
            val passwordHash = password.toSHA256()
            val user = repository.getItemByEmail( email, passwordHash)
            if(user != null){
                Log.d("LOGIN_DEBUG", "Utente trovato: Nome = ${user.nome}, Cognome = ${user.cognome}, Email = ${user.email}")
                sessionManager.salvaSessione(user.codiceFiscale)
                onResult(true)
            }else{
                Log.d("LOGIN_DEBUG", "Nessun utente trovato con queste credenziali")
                onResult(false)
            }
        }
    }

    private val SALT = "Password_ProgettoP_segreta"
    //metodo per criptare la password
    fun String.toSHA256(): String {
        val input = this + SALT //concateno la password con il salt
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray()) // creo la password criptata
        return bytes.joinToString("") { "%02x".format(it) }
    }

}