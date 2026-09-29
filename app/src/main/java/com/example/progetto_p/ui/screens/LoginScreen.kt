package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progetto_p.ui.UtentiViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: UtentiViewModel){

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Bentornato",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondary
            )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Accedi al tuo account",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary
            )

        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(value = viewModel.email , onValueChange = {
            viewModel.email = it
        }, textStyle = TextStyle(
            fontSize = 18.sp
        ),label = {
            Text(text = "Email address",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = viewModel.password, onValueChange = {
            viewModel.password = it
        }, textStyle = TextStyle(
            fontSize = 18.sp
        ), label = {
            Text(text = "Password",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary)
        }, visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                //servono per dare il colore al bordo
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground
            ))

        Spacer(modifier = Modifier.height(16.dp))
        var loginError by remember { mutableStateOf(false) }
        var credenzialiMancate by remember { mutableStateOf(false) }

        Button(onClick = {
            loginError = false
            if (viewModel.email.isBlank() || viewModel.password.isBlank()) {
                println("hai dimenticato di inserire i dati")
                credenzialiMancate = true
            } else {
                viewModel.login(
                    email = viewModel.email,
                    password = viewModel.password,
                    onResult = { esitoPositivo ->
                        if (esitoPositivo) {
                            navController.navigate("QuestionarioSelez")
                        } else {
                            loginError = true
                            Log.d("Login", "Email o password errate")
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
            )){
            Text(text = "Accedi",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary)
        }


        if (credenzialiMancate) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Assicurati che tutti i campi siano completi",
                color = androidx.compose.ui.graphics.Color.Red
            )
        }
        if (loginError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Credenziali non valide o campi vuoti!",
                color = androidx.compose.ui.graphics.Color.Red
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Non sei registrato? Clicca qui", modifier = Modifier.clickable{
            navController.navigate("Registrati")
        },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
            )

    }
}

