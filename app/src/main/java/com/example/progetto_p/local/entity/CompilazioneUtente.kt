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
        )
    ],
    indices = [Index(value = ["questionarioId"])]
    )
data class CompilazioneUtente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val utenteId: Int,
    val questionarioId: String,
    val punteggioTot: Int,
    // val timestamp: Int
)
