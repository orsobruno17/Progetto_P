package com.example.progetto_p.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.progetto_p.local.SessionManager
import com.example.progetto_p.local.dao.CompilazioneDao
import com.example.progetto_p.local.database.AppDatabase
import com.example.progetto_p.local.entity.Domande
import com.example.progetto_p.local.repository.CompilazioneRepository
import com.example.progetto_p.local.repository.CompilazioneViewModelFactory
import com.example.progetto_p.local.repository.DomandeRepository
import com.example.progetto_p.local.repository.DomandeViewModelFactory
import com.example.progetto_p.local.repository.QuestionariRepository
import com.example.progetto_p.local.repository.QuestionariViewModelFactory
import com.example.progetto_p.local.repository.RisposteSelezRepository
import com.example.progetto_p.local.repository.RisposteSelezViewModelFactory
import com.example.progetto_p.local.repository.UtentiRepository
import com.example.progetto_p.local.repository.UtentiViewModelFactory
import com.example.progetto_p.ui.CompilazioneViewModel
import com.example.progetto_p.ui.DomandeViewModel
import com.example.progetto_p.ui.QuestionariViewModel
import com.example.progetto_p.ui.RisposteSelezViewModel
import com.example.progetto_p.ui.UtentiViewModel

@Composable
fun MainNavGraph(database: AppDatabase){
    val navController = rememberNavController()
    val repository = UtentiRepository(database.utenteDao())
    val sessionManager = SessionManager(LocalContext.current)


    NavHost(navController = navController, startDestination = "HomeScreen"){
        composable("HomeScreen"){
            HomeScreen(navController = navController)
        }

        composable("LoginScreen"){
            val factory = UtentiViewModelFactory(repository,sessionManager)
            val utentiViewModel: UtentiViewModel = viewModel(factory = factory)

            LoginScreen(
                navController = navController,
                viewModel = utentiViewModel
            )
        }

        composable( "Registrati" ){

            val factory = UtentiViewModelFactory(repository,sessionManager)
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

            val utenteDao = database.utenteDao()
            val repositoryU = UtentiRepository(utenteDao)
            val factoryU = UtentiViewModelFactory(repositoryU, sessionManager)

            val viewModelU : UtentiViewModel = viewModel(factory = factoryU)

            QuestionarioSelz(
                navController = navController,
                viewModel = viewModel,
                viewModelU = viewModelU
            )
        }

       composable("EseguiQuestionario/{questionarioId}", listOf(
           navArgument("questionarioId"){
               type = NavType.IntType
           }
       )) {
               backStackEntry ->
           val questionarioId = backStackEntry.arguments?.getInt("questionarioId") ?: 0
           val repository = DomandeRepository(database.domandeDao())
           val factory = DomandeViewModelFactory(repository)
           val viewModel: DomandeViewModel = viewModel(factory = factory)

           val questionariDao = database.questionarioDao()
           val questionariRepository = QuestionariRepository(questionariDao)
           val questionariFactory = QuestionariViewModelFactory(questionariRepository)
           val viewModelQ: QuestionariViewModel = viewModel(factory = questionariFactory)

           val risposteDao = database.risposteSelezionataDao()
           val risposteRepository = RisposteSelezRepository(risposteDao)
           val risposteFactory = RisposteSelezViewModelFactory( risposteRepository,sessionManager)
           val viewModelS: RisposteSelezViewModel = viewModel(factory = risposteFactory)

           val compilazioneDao = database.compilazioneDao()
           val compilazioneRepository = CompilazioneRepository(compilazioneDao)
           val compilazioneFactory = CompilazioneViewModelFactory(compilazioneRepository)
           val viewModelC: CompilazioneViewModel = viewModel(factory = compilazioneFactory)

           EseguiQuestionario(
               questionarioId = questionarioId,
               navController = navController,
               viewModel = viewModel,
               viewModelQ = viewModelQ,
               viewModelS= viewModelS,
               viewModelC= viewModelC
           )

        }

        composable ( "OutputScreen/{questionarioId}", listOf(
            navArgument("questionarioId"){
                type = NavType.IntType
            },
            )){
                backStackEntry ->
            val questionarioId = backStackEntry.arguments?.getInt("questionarioId") ?: 0
            //val nuovoValore = backStackEntry.arguments?.getInt("nuovoValore") ?: 0
            val compilazioneDao = database.compilazioneDao()
            val repository = CompilazioneRepository(compilazioneDao)
            val factory = CompilazioneViewModelFactory(repository)

            val viewModel: CompilazioneViewModel = viewModel(factory = factory)
            val risposteDao = database.risposteSelezionataDao()
            val risposteRepository = RisposteSelezRepository(risposteDao)
            val risposteFactory = RisposteSelezViewModelFactory( risposteRepository,sessionManager)
            val viewModelS: RisposteSelezViewModel = viewModel(factory = risposteFactory)


            OutputScreen(
                questionarioId = questionarioId,
                //nuovoValore = nuovoValore,
                navController = navController,
                viewModel = viewModel,
                viewModelS = viewModelS
            )
        }

    }
}
