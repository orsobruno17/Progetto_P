package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
    navController: NavController,
    viewModel: CompilazioneViewModel,
    viewModelS: RisposteSelezViewModel
){
    val codiceFiscaleUser by viewModelS.utenteLog.collectAsStateWithLifecycle()
    //val punteggio = nuovoValore
    val punteggio by if (codiceFiscaleUser != null) {
        viewModel.getTotPunteggio(codiceFiscaleUser!!, questionarioId)
            .collectAsStateWithLifecycle(initialValue = null)
    } else {
        remember { mutableStateOf(null) }
    }
    Log.d("DEBUG PUNTEGGIO", "Punteggio: ${punteggio}")

    val testoRisul by if (punteggio != null) {
        viewModel.getRisultato(questionarioId, punteggio!!)
            .collectAsStateWithLifecycle(initialValue = "Caricamento esito...")
    } else {
        remember { mutableStateOf(null) }
    }

    if (codiceFiscaleUser == null || punteggio == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Questionario Completato",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary)

                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = "Punteggio: $punteggio",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = testoRisul ?: "Nessun risultato disponibile per questo punteggio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                    )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = {
                    navController.navigate("QuestionarioSelez")
                }, modifier = Modifier.defaultMinSize(minWidth = 80.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.onBackground)) {
                    Text(text = "Torna alla HomePage",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary)
                }
            }

    }
}