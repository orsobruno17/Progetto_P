package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.progetto_p.local.entity.Questionari
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionariDao {
    @Query("SELECT * FROM questionari")
    fun getAllItems(): Flow<List<Questionari>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionario: Questionari)
}