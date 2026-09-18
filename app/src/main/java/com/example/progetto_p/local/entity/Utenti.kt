package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dati_utente")
data class Utenti(
    @PrimaryKey(autoGenerate = false)
    val codiceFiscale: String,
    val nome: String,
    val cognome: String,
    val email: String,
    val password: String
)
