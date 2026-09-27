package com.example.progetto_p.ui.screens

import android.R.attr.onClick
import android.preference.PreferenceActivity
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.example.progetto_p.ui.UtentiViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class) //lo devo mettere per avere la parte iniziale ferma
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
    val domande by viewModel.domande.collectAsStateWithLifecycle()

    //qui metto tutte le risposte dell'utente con chiave =domanda.id e il valore= rispostaSelezionata
    var risposteSelezionate by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    //permette di caricare il valore in background
    val titoloQuestionario by produceState(initialValue = "Caricamento...", key1 = questionarioId) {
        value = (viewModelQ.getTitolo(questionarioId) ?: "Titolo non disponibile")
    }

    //da questo prendo il codice e di conseguenza l'id (forse devo fare una query per farmi ritornare l'id)
    //serve per la tabella compilazione
    val codiceFiscaleUser by viewModelS.utenteLog.collectAsStateWithLifecycle()

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
    val compilazioneId by viewModelC.getCompl(codiceFiscaleUser!!, questionarioId).collectAsStateWithLifecycle(initialValue = 0)

    //il LauchedEffect permette di creare la compilazione solo una volta, anche se c'è una ricomposizione
    LaunchedEffect(codiceFiscaleUser, questionarioId) {
        if (compilazioneId == 0) {
            viewModelC.inserisciCompil(codiceFiscaleUser!!, questionarioId)
        }
    }

Column(modifier = Modifier
    .fillMaxSize()
    .padding(16.dp)) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        stickyHeader {
            Text(text = titoloQuestionario)
        }
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
        }) {
            Text(text = "Indietro")
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
                    navController.navigate("OutputScreen/${questionarioId}")
                    //navController.navigate("OutputScreen/${questionarioId}/\${nuovoValore}")
                }

            }
        }) {
            Text(text = "Fine")
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = domanda.testo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
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
                        onClick = null
                    )
                    Text(
                        text = risposta.testo,
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
