package com.example.progetto_p.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.progetto_p.local.entity.Questionari
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.UtentiViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionarioSelz(navController: NavController, viewModel: QuestionariViewModel, viewModelU: UtentiViewModel){
    // riesce a leggere la lista di questionari aggiornata dal ViewModel
    val questionariList by viewModel.items.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = "Seleziona Questionario",
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.onSecondary)},
                actions = {
                    TextButton(
                        onClick = { viewModelU.logout() },
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.onBackground //colore dello sfondo
                        )
                    ){
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = " Logout",
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiary)
            ){
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
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
    Card(
        modifier = Modifier
            .clickable { onClick() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(10.dp, MaterialTheme.colorScheme.onBackground),
        ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = questionario.titolo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = questionario.descrizione,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))

            questionario.fasciaEta?.let{
                eta ->
                Text(
                    text = "Fascia età: $eta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            questionario.tempoCompletamento?.let{
                    tempo ->
                Text(
                    text = "Tempo di completamento: $tempo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            questionario.frequenzaUso?.let{
                    frequenza ->
                Text(
                    text = "Frequenza d'uso: $frequenza",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
    }

