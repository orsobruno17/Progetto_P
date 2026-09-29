package com.example.progetto_p

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.progetto_p.local.database.AppDatabase
import com.example.progetto_p.ui.screens.MainNavGraph
import com.example.progetto_p.ui.theme.Progetto_PTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getDatabase(context = applicationContext)

        setContent {
            Progetto_PTheme {
                MainNavGraph(database = database)
            }
        }
    }
}