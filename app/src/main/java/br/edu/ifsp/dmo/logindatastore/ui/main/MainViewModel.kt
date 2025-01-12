package br.edu.ifsp.dmo.logindatastore.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.logindatastore.data.DataStoreRepository
import br.edu.ifsp.dmo.logindatastore.data.User
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DataStoreRepository(application)

    val loginPreferences: LiveData<Pair<Boolean, Boolean>> = repository.loginPreferences.asLiveData()
    val storedData: LiveData<Pair<String, String>> = repository.storedData.asLiveData()

    private val _connected = MutableLiveData<Boolean>()
    val connected: LiveData<Boolean> = _connected

    fun login(
        login: String,
        password: String,
        flgSaveLogin: Boolean,
        flgStayConnected: Boolean): Boolean {

        if (User.autenticate(login, password)) {
            if (flgSaveLogin) {
                savePreferences(login, password, flgSaveLogin, flgStayConnected)
            }
            else {
                savePreferences("", "", false, flgStayConnected)
            }
            return true
        }

        return false
    }

    private fun savePreferences(
        login: String,
        password: String,
        flgSaveLogin: Boolean,
        flgStayConnected: Boolean) {
        viewModelScope.launch {
            repository.savePreferences(login, password, flgSaveLogin, flgStayConnected)
        }
    }
}