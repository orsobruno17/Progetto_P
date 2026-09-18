package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "compilazioneUtente",
    foreignKeys = [
        ForeignKey(
            entity = CompilazioneUtente::class,
            parentColumns = ["id"],
            childColumns = ["compilazione_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Domande::class,
            parentColumns = ["id"],
            childColumns = ["domanda_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OpzioniRisposta::class,
            parentColumns = ["id"],
            childColumns = ["opzione_selezionata_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["compilazioneId"]),
        Index(value = ["domandaId"]),
        Index(value = ["opzioneSelezionataId"])
    ]
    )
data class RispostaSelezionata(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val compilazioneId: Int,
    val domandaId: Int,
    val opzioneSelezionataId: Int
)