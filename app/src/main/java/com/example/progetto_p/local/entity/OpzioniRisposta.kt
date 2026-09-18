package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "opzioniRisposta",
        foreignKeys = [
            ForeignKey(
                entity = Domande::class,
                parentColumns = ["id"],
                childColumns = ["domandaId"],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [Index(value = ["domandaId"])]
)
data class OpzioniRisposta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val domandaId: Int,
    val testo: String,
    val punteggio: Int,
    val ordine: Int
)