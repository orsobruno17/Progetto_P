package com.example.progetto_p.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.entity.OpzioniRisposta
import com.example.progetto_p.ui.CompilazioneViewModel
import com.example.progetto_p.ui.DomandeViewModel
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.RisposteSelezViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EseguiQuestionario(
    questionarioId: Int,
    navController: NavController,
    viewModel: DomandeViewModel,
    viewModelQ: QuestionariViewModel,
    viewModelS: RisposteSelezViewModel,
    viewModelC: CompilazioneViewModel
){
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val domande by viewModel.getDomandeById(questionarioId)
        .collectAsStateWithLifecycle(initialValue = emptyList())

    //qui metto tutte le risposte dell'utente con chiave =domanda.id e il valore= rispostaSelezionata
    var risposteSelezionate by rememberSaveable { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    //da questo prendo il codice e di conseguenza l'id (forse devo fare una query per farmi ritornare l'id)
    //serve per la tabella compilazione
    val codiceFiscaleUser by viewModelS.utenteLog.collectAsStateWithLifecycle(initialValue = null)

    if (codiceFiscaleUser == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    //recupero l'id della compilazione
    val compilazioneId by viewModelC.getCompl(codiceFiscaleUser!!, questionarioId).collectAsStateWithLifecycle(initialValue = -1)

    //il LauchedEffect permette di creare la compilazione solo una volta, anche se c'è una ricomposizione
    LaunchedEffect(codiceFiscaleUser, questionarioId, compilazioneId) {
        if (compilazioneId == 0) {
            viewModelC.inserisciCompil(codiceFiscaleUser!!, questionarioId)
        }
    }

    if (compilazioneId <= 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = "Compila Questionario",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.onSecondary)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.tertiary)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(domande)
                { domanda ->
                    //questo permette di ascoltare il Flow o lo converte in uno State di compose
                    val risposte by viewModel.risposte(domanda.id)
                        .collectAsStateWithLifecycle(initialValue = emptyList())
                    DomandeCard(
                        domanda = domanda,
                        risposte = risposte, //SONO LE RISPOSTE POSSIBILI
                        //serve per verificare quale radiobutton è stato selezionato e viene salvato in memoria
                        idRispostaSelezionata = risposteSelezionate[domanda.id],
                        onRispostaSelezionata = { rispostaId ->
                            risposteSelezionate = risposteSelezionate + (domanda.id to rispostaId)
                            //QUI GLI PASSO IL VALORE DELLA COMPILAZIONE
                            if(compilazioneId > 0) {
                                viewModelS.salvaRispostaCompilata(compilazioneId, domanda.id, rispostaId)
                            }else{
                                Log.d("DEBUG", "la compilazione non è ancora avvenuta")
                            }
                        }

                    )

                }


            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    navController.navigate("QuestionarioSelez")
                }, modifier = Modifier.height(56.dp)
                    .defaultMinSize(minWidth = 80.dp)
                    .padding(start = 7.dp, bottom = 6.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.onBackground)) {
                    Text(text = "Indietro",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary)
                    Spacer(Modifier.height(4.dp))
                }

                Button(onClick = {
                    if (domande.size != risposteSelezionate.size) {
                        Toast.makeText(context, "Devi rispondere a tutte le domande", Toast.LENGTH_LONG).show()
                    } else {
                        //QUI FACCIO LA SOMMA
                        scope.launch {
                            val nuovoValore = viewModelC.sommaTotPunt(compilazioneId)
                            Log.d("DEBUG","LA SOMMA VALE ${nuovoValore}")
                            //INSERISCO IL TOTALE IN compilazioneUtente punteggio tot
                            viewModelC.aggiornaPunteggioT(nuovoValore, compilazioneId)
                            navController.navigate("OutputScreen/${questionarioId}"){
                                //con popUpTo cancello tutti gli screen fino ad adesso così evito che l'utente se va indietro non succede nulla
                                //inclusive dico che anche la prima schermata viene eliminata
                                popUpTo(0) { inclusive = true }
                            }
                            //navController.navigate("OutputScreen/${questionarioId}/\${nuovoValore}")
                        }
                    }
                }, modifier = Modifier.height(56.dp)
                    .padding(end = 7.dp, bottom = 6.dp)
                    .defaultMinSize(minWidth = 80.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.onBackground)) {
                    Text(text = "Fine",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary)
                    Spacer(Modifier.height(4.dp))
                }
            }
    }


}

}


@Composable
fun DomandeCard(
    domanda: Domande,
    risposte: List<OpzioniRisposta>,
    idRispostaSelezionata: Int?,
    onRispostaSelezionata: (Int) -> Unit

    ){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(10.dp, MaterialTheme.colorScheme.onBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = domanda.testo,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))

            risposte.forEach { risposta ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        //l'utente seleziona la risposta
                        .clickable { onRispostaSelezionata(risposta.id) }
                        .padding(vertical = 4.dp)
                ){
                    RadioButton(
                        selected = (risposta.id == idRispostaSelezionata),
                        onClick = null,
                        colors = RadioButtonDefaults.colors( selectedColor = MaterialTheme.colorScheme.secondary,
                            disabledSelectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = risposta.testo,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}
