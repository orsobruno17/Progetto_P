package com.example.progetto_p.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.progetto_p.local.database.AppDatabase
import com.example.progetto_p.local.repository.QuestionariRepository
import com.example.progetto_p.local.repository.QuestionariViewModelFactory
import com.example.progetto_p.local.repository.UtentiRepository
import com.example.progetto_p.local.repository.UtentiViewModelFactory
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.UtentiViewModel

@Composable
fun MainNavGraph(database: AppDatabase){
    val navController = rememberNavController()
    //val database = AppDatabase
    val repository = UtentiRepository(database.utenteDao())

    NavHost(navController = navController, startDestination = "HomeScreen"){
        composable("HomeScreen"){
            HomeScreen(navController = navController)
        }

        composable("LoginScreen"){
            val factory = UtentiViewModelFactory(repository)
            val utentiViewModel: UtentiViewModel = viewModel(factory = factory)

            LoginScreen(
                navController = navController,
                viewModel = utentiViewModel
            )
        }

        composable( "Registrati" ){

            val factory = UtentiViewModelFactory(repository)
            val utentiViewModel: UtentiViewModel = viewModel(factory = factory)

            RegistrazioneScreen(
                navController = navController,
                viewModel = utentiViewModel
            )
        }

        composable( "QuestionarioSelez" ){
            val questionariDao = database.questionarioDao()
            val repository = QuestionariRepository(questionariDao)
            val factory = QuestionariViewModelFactory(repository)

            val viewModel: QuestionariViewModel = viewModel(factory = factory)

            QuestionarioSelz(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
