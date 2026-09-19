package com.example.progetto_p.local.repository

import com.example.progetto_p.local.dao.QuestionariDao
import com.example.progetto_p.local.entity.Questionari
import kotlinx.coroutines.flow.Flow

class QuestionariRepository (
    private val dao : QuestionariDao
){
    val items: Flow<List<Questionari>> = dao.getAllItems()

}