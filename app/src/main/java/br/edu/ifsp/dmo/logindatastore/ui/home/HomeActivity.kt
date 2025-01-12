package br.edu.ifsp.dmo.logindatastore.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.logindatastore.databinding.ActivityHomeBinding
import br.edu.ifsp.dmo.logindatastore.ui.main.MainActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.textViewMessage.text = "Bem-vindo!"

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonLogout.setOnClickListener {
            handleLogout()
        }
    }

    private fun handleLogout() {
        viewModel.logout()
        val mIntent = Intent(this, MainActivity::class.java)
        startActivity(mIntent)
        finish()
    }
}