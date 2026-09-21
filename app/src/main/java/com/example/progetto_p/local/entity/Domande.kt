package com.example.progetto_p.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "domande",
    foreignKeys = [
        ForeignKey(
            entity = Questionari::class,
            parentColumns = ["id"],
            childColumns = ["questionarioId"],
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["questionarioId"])]
)
data class Domande(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questionarioId: Int,
    val testo: String,
    val categoria: String?,
    val ordine: Int,
    //val dipendeDaDomandaId: Int? = null
    )