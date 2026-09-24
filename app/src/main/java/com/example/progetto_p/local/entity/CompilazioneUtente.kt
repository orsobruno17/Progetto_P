package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "compilazioneUtente",
    foreignKeys = [
        ForeignKey(
            entity = Questionari::class,
            parentColumns = ["id"],
            childColumns = ["questionarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Utenti::class,
            parentColumns = ["codiceFiscale"],
            childColumns = ["utenteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["questionarioId"]),
        Index(value = ["utenteId"])]
    )
data class CompilazioneUtente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val utenteId: String,
    val questionarioId: Int,
    val punteggioTot: Int?,
    // val timestamp: Int
)
