package com.example.progetto_p.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.progetto_p.R
import com.example.progetto_p.ui.UtentiViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: UtentiViewModel){
    var email by remember {
        mutableStateOf("")
    }

    var password by remember() {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.login),contentDescription = "Login immagine", modifier = Modifier.size(200.dp) )

        Text(text = "Bentornato", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Accedi al tuo account")

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email , onValueChange = {
            email = it
        }, label = {
            Text(text = "Email address")
        })

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(16.dp))
        var loginError by remember { mutableStateOf(false) }
        var credenzialiMancate by remember { mutableStateOf(false) }

        Button(onClick = {
            loginError = false
            if (email.isBlank() || password.isBlank()) {
                println("hai dimenticato di inserire i dati")
                credenzialiMancate = true
            } else {
                viewModel.login(
                    email = email,
                    password = password,
                    onResult = { esitoPositivo ->
                        if (esitoPositivo) {
                            navController.navigate("QuestionarioSelez") {
                                // Cancella la schermata di login dal backstack,
                                // così se l'utente preme "Indietro" dal tablet non torna al login
                                popUpTo("Login") { inclusive = true }
                            }
                        } else {
                            loginError = true
                            Log.d("Login", "Email o password errate")
                        }
                    }
                )
            }
        }){
            Text(text = "Accedi")
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
        })

    }
}

