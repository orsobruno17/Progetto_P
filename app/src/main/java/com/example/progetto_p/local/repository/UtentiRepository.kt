package com.example.progetto_p.local.repository

import com.example.progetto_p.local.dao.UtenteDao
import com.example.progetto_p.local.entity.Utenti
import kotlinx.coroutines.flow.Flow

class UtentiRepository(
//repository fa da intermediario tra ui e il database
    private val dao: UtenteDao
) {

    //Prende parametri semplici dalla UI
    //crea l'oggetto Utenti e lo passa al DAO per l'inserimento.
    suspend fun addItem(codiceFiscale: String, nome: String, cognome: String, email: String, password: String) {
        dao.insertItem(
            Utenti(
                codiceFiscale = codiceFiscale,
                nome = nome,
                cognome = cognome,
                email = email,
                password = password
            )
        )
    }

    suspend fun getItemByEmail(email: String, password: String): Utenti?{
        return dao.getItemByEmail(email,password)
    }
}