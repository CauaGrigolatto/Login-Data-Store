package br.edu.ifsp.dmo.logindatastore.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.logindatastore.R
import br.edu.ifsp.dmo.logindatastore.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.logindatastore.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.connected.observe(this, Observer {
            if (it) {
                navigateToHomeActivity()
            }
            else {
                shortToast("Erro ao fazer login.")
            }
        })

        viewModel.loginPreferences.observe(this, Observer {
            val (flgSaveLogin, flgStayConnected) = it

            if (flgStayConnected) {
                navigateToHomeActivity()
            }

            binding.checkboxSaveLogin.isChecked = flgSaveLogin
            binding.checkboxStayConnected.isChecked = flgStayConnected
        })

        viewModel.storedData.observe(this, Observer {
            val (login, password) = it

            binding.textInputLogin.setText(login)

            if (login.isNotBlank()) {
                binding.textInputPassword.setText(password)
            }
        })
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val login = binding.textInputLogin.text.toString()
        val password = binding.textInputPassword.text.toString()
        val flgSaveLogin = binding.checkboxSaveLogin.isChecked
        val flgStayConnected = binding.checkboxStayConnected.isChecked

        if (login.isNotBlank() && password.isNotBlank()) {
            if (viewModel.login(login, password, flgSaveLogin, flgStayConnected)) {
                navigateToHomeActivity()
            }
            else {
                shortToast("Login ou senha inválido.")
            }
        }
        else {
            shortToast("Preencha todos os campos.")
        }
    }

    private fun navigateToHomeActivity() {
        val mIntent = Intent(this, HomeActivity::class.java)
        startActivity(mIntent)
        finish()
    }

    private fun shortToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}