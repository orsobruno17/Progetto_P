package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.progetto_p.ui.CompilazioneViewModel
import com.example.progetto_p.ui.RisposteSelezViewModel

@Composable
fun OutputScreen(
    questionarioId: Int,
    //nuovoValore: Int,
    navController: NavController,
    viewModel: CompilazioneViewModel,
    viewModelS: RisposteSelezViewModel
){
    /*val codiceFiscaleUser by viewModelS.utenteLog.collectAsStateWithLifecycle()
    val punteggio by  viewModel.getTotPunteggio(codiceFiscaleUser?: "", questionarioId).collectAsStateWithLifecycle(initialValue = -1)
    Log.d("DEBUG", "Punteggio: ${punteggio}")
    val testoRisul by viewModel.getRisultato(questionarioId, punteggio).collectAsStateWithLifecycle(initialValue = "Caricamento..")

    if (codiceFiscaleUser == null || punteggio == -1) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Punteggio: $punteggio")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = testoRisul ?: "Nessun risultato disponibile per questo punteggio")
            }
        }
    }

*/
    // 1. Leggi l'utente loggato
    val codiceFiscaleUser by viewModelS.utenteLog.collectAsStateWithLifecycle()
    //val punteggio = nuovoValore
    val punteggio by if (codiceFiscaleUser != null) {
        viewModel.getTotPunteggio(codiceFiscaleUser!!, questionarioId)
            .collectAsStateWithLifecycle(initialValue = null)
    } else {
        remember { mutableStateOf(null) }
    }
    Log.d("DEBUG PUNTEGGIO", "Punteggio: ${punteggio}")

    // 3. Raccogli il risultato SOLO quando il punteggio è stato recuperato dal DB (non è null)
    val testoRisul by if (punteggio != null) {
        viewModel.getRisultato(questionarioId, punteggio!!)
            .collectAsStateWithLifecycle(initialValue = "Caricamento esito...")
    } else {
        remember { mutableStateOf(null) }
    }

    // 4. Renderizza la UI in base allo stato dei dati
    if (codiceFiscaleUser == null || punteggio == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Punteggio: $punteggio")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = testoRisul ?: "Nessun risultato disponibile per questo punteggio")
            }
        }
    }
}