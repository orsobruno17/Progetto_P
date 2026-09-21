package com.example.progetto_p

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavGraph
import com.example.progetto_p.local.database.AppDatabase
import com.example.progetto_p.local.repository.UtentiRepository
import com.example.progetto_p.ui.screens.LoginScreen
import com.example.progetto_p.ui.screens.MainNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val applicationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        val database = AppDatabase.getDatabase(context = applicationContext)

        //val repository = UtentiRepository(database.utenteDao())

        setContent {
            MainNavGraph(database = database)
        }
    }
}