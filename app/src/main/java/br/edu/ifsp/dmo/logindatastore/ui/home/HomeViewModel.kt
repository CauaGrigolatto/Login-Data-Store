package br.edu.ifsp.dmo.logindatastore.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.logindatastore.data.DataStoreRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DataStoreRepository(application)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}