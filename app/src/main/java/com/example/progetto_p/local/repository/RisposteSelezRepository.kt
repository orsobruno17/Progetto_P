package com.example.progetto_p.local.repository

import com.example.progetto_p.local.dao.CompilazioneDao
import com.example.progetto_p.local.dao.DomandeDao
import com.example.progetto_p.local.dao.RisposteSelezDao
import com.example.progetto_p.local.entity.OpzioniRisposta
import com.example.progetto_p.local.entity.RispostaSelezionata
import kotlinx.coroutines.flow.Flow

class RisposteSelezRepository(
    private val dao: RisposteSelezDao
) {
    suspend fun salvaRisposta(risposta: RispostaSelezionata){
        return dao.salvaRisposta(risposta)
    }

}