package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progetto_p.R
import com.example.progetto_p.ui.UtentiViewModel


@Composable
fun RegistrazioneScreen(navController: NavController, viewModel: UtentiViewModel) {
    var passwordError by remember { mutableStateOf(false) }
    var cfError by remember { mutableStateOf(false) }
    var credenzialiMancate by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registrazione",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = viewModel.codiceFiscale,
            onValueChange = { viewModel.codiceFiscale = it
                            cfError = false},
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            label = {
                Text(
                    text = "Codice Fiscale",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = viewModel.nome, onValueChange = { viewModel.nome = it }, textStyle = TextStyle(
            fontSize = 18.sp
        ), label = {
            Text(text = "Nome",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary)
        },
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.cognome,
            onValueChange = { viewModel.cognome = it }, textStyle = TextStyle(
                fontSize = 18.sp
            ),
            label = {
                Text(text = "Cognome",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary)
            },
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it }, textStyle = TextStyle(
                fontSize = 18.sp
            ),
            label = {
                Text(text = "Email address",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary)
            },
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it
                            passwordError = false},
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            label = {
                Text(text = "Password",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary)
            }, visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            credenzialiMancate = false
            cfError = false
            passwordError = false
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
                    onResult = { esito, erroreCF, errorePassword ->
                        if (esito) {
                            navController.navigate("QuestionarioSelez")
                        } else {
                            cfError = erroreCF
                            passwordError = errorePassword
                        }
                    }
                )
            }

        },
            modifier = Modifier.height(56.dp)
                .defaultMinSize(minWidth = 160.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.onBackground //colore dello sfondo
            )) {
            Text(text = "Registrati")
        }
        if (credenzialiMancate) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Assicurati che tutti i campi siano completi",
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = 12.sp
            )
        }
        if (cfError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Il Codice Fiscale inserito non è valido!",
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = 12.sp
            )
        }
        if (passwordError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "La password non contiene almeno un numero e/o un numero speciale!",
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = 12.sp
            )
        }

    }
}