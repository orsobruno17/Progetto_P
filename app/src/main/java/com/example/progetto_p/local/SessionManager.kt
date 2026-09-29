package com.example.progetto_p.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
private const val USER = "user_codicefiscale"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER)

class SessionManager(private val context: Context) {
    //rappresenta il pacchetto
    private object PreferencesKeys{
        //ci metto una chiave dove salverò il codicefiscale dell'utente
        val codiceFiscale = stringPreferencesKey("codiceFiscale_utente")
    }

    //qui apro il pacchetto e cerco il codicefiscale tramite .map
    val codiceFiscaleFlow: Flow<String?> = context.dataStore.data
        //se ci sono erroir di lettura restituisce nell
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                //se sono altri tipi di errori allora lancia errore
                throw exception
            }
        }
        .map{
            preferences ->
            preferences[PreferencesKeys.codiceFiscale]
        }

    //serve per memorizzare il codicefiscale nella memoria del dispositivo
    suspend fun salvaSessione(codiceFiscale: String){
        context.dataStore.edit{
            preferences ->
            preferences[PreferencesKeys.codiceFiscale] = codiceFiscale
        }
    }

    suspend fun cancellaSessione(){
        context.dataStore.edit {
            preferences ->
            preferences.remove(PreferencesKeys.codiceFiscale)
        }
    }
}