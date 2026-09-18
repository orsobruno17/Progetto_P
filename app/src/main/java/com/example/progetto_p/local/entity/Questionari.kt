package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionari")
data class Questionari (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val titolo: String,
    val descrizione: String,
    val fasciaEta: String?,
    val tempoCompletamento: String?,
    val frequenzaUso: String?
    )