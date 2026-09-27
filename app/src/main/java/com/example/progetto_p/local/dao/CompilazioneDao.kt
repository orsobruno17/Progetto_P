package com.example.progetto_p.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.progetto_p.local.entity.CompilazioneUtente
import kotlinx.coroutines.flow.Flow

@Dao
interface CompilazioneDao {
    @Insert
    suspend fun complUtente(compilazione: CompilazioneUtente)

    @Query("SELECT id FROM compilazioneUtente WHERE utenteId = :utenteId AND questionarioId = :questionarioId ORDER BY id DESC LIMIT 1")
    fun getCompl(utenteId: String, questionarioId: Int): Flow<Int>

    //@Query("SELECT COUNT(punteggio) FROM compilazioneUtente c INNER JOIN rispostaSelezionata r ON c.id = r.compilazioneId AND rispostaSelezionata r INNER JOIN opzioniRisposta o ON r.domandaId = o.domandaId")
    @Query("SELECT SUM(o.punteggio) FROM CompilazioneUtente c INNER JOIN RispostaSelezionata r ON c.id = r.compilazioneId INNER JOIN OpzioniRisposta o ON r.opzioneSelezionataId = o.id WHERE c.id = :compilazioneId")
    suspend fun sommaTotPunt(compilazioneId: Int): Int

    @Query("UPDATE compilazioneUtente SET punteggioTot= :nuovoValore WHERE id = :compilazioneId")
    suspend fun aggiornaPunteggioT(nuovoValore: Int, compilazioneId: Int)

    @Query("SELECT punteggioTot FROM compilazioneUtente WHERE utenteId= :utenteId AND questionarioId= :questionarioId ORDER BY id DESC LIMIT 1")
    fun getTotPunteggio(utenteId: String, questionarioId: Int): Flow<Int>

    @Query("SELECT testo FROM risultati WHERE questionarioId = :questionariId AND :punteggio BETWEEN range1 AND range2")
    fun getRisultato(questionariId: Int, punteggio: Int): Flow<String>

}