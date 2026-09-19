package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "rispostaSelezionata",
    foreignKeys = [
        ForeignKey(
            entity = CompilazioneUtente::class,
            parentColumns = ["id"],
            childColumns = ["compilazioneId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Domande::class,
            parentColumns = ["id"],
            childColumns = ["domandaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OpzioniRisposta::class,
            parentColumns = ["id"],
            childColumns = ["opzioneSelezionataId"],
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