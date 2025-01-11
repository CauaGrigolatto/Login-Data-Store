package br.edu.ifsp.dmo.logindatastore.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import br.edu.ifsp.dmo.logindatastore.data.DataStoreRepository
import androidx.datastore.preferences.core.Preferences

// Configuração do DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    // Pode ser substituído por um valor da classe ou constante
    name = DataStoreRepository.PreferencesFile.FILE_NAME
)