package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "risultati",
    foreignKeys = [
        ForeignKey(
            entity = Questionari::class,
            parentColumns = ["id"],
            childColumns = ["questionarioId"],
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["questionarioId"])]
    )
data class Risultati(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val testo: String,
    val range1: Int,
    val range2: Int,
    val questionarioId: Int
)
