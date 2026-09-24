package com.example.progetto_p.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.theme.Navy

@Composable
fun QuestionarioSelz(navController: NavController, viewModel: QuestionariViewModel){
    // riesce a leggere la lista di questionari aggiornata dal ViewModel
    val questionariList by viewModel.items.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)){

            Text(text = "Seleziona il tuo questionario", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                items(questionariList) { questionario ->
                    QuestionarioCard(
                        questionario = questionario,
                        onClick = {
                            navController.navigate("EseguiQuestionario/${questionario.id}")
                        }

                    )
                }
            }
        }

    }
}
@Composable
fun QuestionarioCard(
    questionario: Questionari,
    onClick: () -> Unit
){
    Column(
            modifier = Modifier
                .background(Navy)
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = questionario.titolo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = questionario.descrizione,
                fontSize = 14.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            questionario.fasciaEta?.let{
                eta ->
                Text(
                    text = "Fascia età: $eta",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }

            questionario.tempoCompletamento?.let{
                    tempo ->
                Text(
                    text = "Tempo di completamento: $tempo",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }

            questionario.frequenzaUso?.let{
                    frequenza ->
                Text(
                    text = "Frequenza d'uso: $frequenza",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }

