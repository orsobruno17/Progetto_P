package com.example.progetto_p.local.repository

import com.example.progetto_p.local.dao.DomandeDao
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import kotlinx.coroutines.flow.Flow

class DomandeRepository(
    private val dao: DomandeDao
) {

    fun getDomandeById(questionarioId: Int): Flow<List<Domande>> {
        return dao.getDomandeById(questionarioId)
    }

    fun getRisposte(domandaId: Int): Flow<List<OpzioniRisposta>> {
        return dao.getRisposte(domandaId)
    }


}