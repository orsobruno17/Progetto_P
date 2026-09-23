package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat

import androidx.navigation.NavController
import com.example.progetto_p.R
import com.example.progetto_p.ui.UtentiViewModel
import java.security.MessageDigest


@Composable
fun RegistrazioneScreen(navController: NavController, viewModel: UtentiViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login immagine",
            modifier = Modifier.size(100.dp)
        )

        Text(text = "Registrati", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = viewModel.codiceFiscale,
            onValueChange = { viewModel.codiceFiscale = it },
            label = {
                Text(text = "Codice Fiscale")
            })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = viewModel.nome, onValueChange = { viewModel.nome = it }, label = {
            Text(text = "Nome")
        })

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.cognome,
            onValueChange = { viewModel.cognome = it },
            label = {
                Text(text = "Cognome")
            })

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = {
                Text(text = "Email address")
            })

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = {
                Text(text = "Password")
            })

        Spacer(modifier = Modifier.height(16.dp))
        var passwordError by remember { mutableStateOf(false) }
        var credenzialiMancate by remember { mutableStateOf(false) }

        Button(onClick = {
            if (viewModel.codiceFiscale.isBlank() || viewModel.nome.isBlank() || viewModel.cognome.isBlank() || viewModel.email.isBlank() || viewModel.password.isBlank()) {
                println("hai dimenticato di inserire i dati")
                credenzialiMancate = true
            } else {
                viewModel.registrazione(
                    codiceFiscale = viewModel.codiceFiscale,
                    nome = viewModel.nome,
                    cognome = viewModel.cognome,
                    email = viewModel.email,
                    password = viewModel.password,
                    onResult = { esito ->
                        if (esito) {
                            navController.navigate("QuestionarioSelez")
                        } else {
                            passwordError = true
                            Log.d(
                                "LOGIN_DEBUG",
                                "La password non contiene almeno un numero e carattere speciale"
                            )
                        }
                    }
                )
            }

        }) {
            Text(text = "Registrati")
        }
        if (credenzialiMancate) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Assicurati che tutti i campi siano completi",
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
        if (passwordError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "La password non contiene almeno un numero e/o un numero speciale!",
                color = androidx.compose.ui.graphics.Color.Red
            )
        }

    }
}