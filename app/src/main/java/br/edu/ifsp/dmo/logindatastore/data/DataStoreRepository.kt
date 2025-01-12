package br.edu.ifsp.dmo.logindatastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.edu.ifsp.dmo.logindatastore.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    object PreferencesFile {
        const val FILE_NAME = "user_preferences"
    }

    private object PreferencesKeys {
        val FLG_SAVE_LOGIN = booleanPreferencesKey("flg_save_login")
        val FLG_STAY_CONNECTED = booleanPreferencesKey("flg_stay_connected")
        val LOGIN = stringPreferencesKey("login")
        val PASSWORD = stringPreferencesKey("password")
    }

    suspend fun savePreferences(
        login: String = "",
        password: String = "",
        flgSaveLogin: Boolean = false,
        flgStayConnected: Boolean = false) {

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOGIN] = login
            preferences[PreferencesKeys.PASSWORD] = password
            preferences[PreferencesKeys.FLG_SAVE_LOGIN] = flgSaveLogin
            preferences[PreferencesKeys.FLG_STAY_CONNECTED] = flgStayConnected
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FLG_STAY_CONNECTED] = false
        }
    }

    val loginPreferences: Flow<Pair<Boolean, Boolean>> = dataStore.data.map { preferences ->
        val flgSaveLogin = preferences[PreferencesKeys.FLG_SAVE_LOGIN] ?: false
        val flgStayConnected = preferences[PreferencesKeys.FLG_STAY_CONNECTED] ?: false
        Pair(flgSaveLogin, flgStayConnected)
    }

    val storedData: Flow<Pair<String, String>> = dataStore.data.map { preferences ->
        val login = preferences[PreferencesKeys.LOGIN] ?: ""
        val password = preferences[PreferencesKeys.PASSWORD] ?: ""
        Pair(login, password)
    }
}