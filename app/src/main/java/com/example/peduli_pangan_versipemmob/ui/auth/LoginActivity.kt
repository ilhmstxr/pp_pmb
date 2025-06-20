package com.example.peduli_pangan_versipemmob.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.peduli_pangan_versipemmob.MainActivity
import com.example.peduli_pangan_versipemmob.databinding.ActivityLoginBinding
import com.example.peduli_pangan_versipemmob.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.authState.observe(this) { state ->
            when (state) {
                is AuthViewModel.AuthState.Loading -> {
                    binding.progressBar?.isVisible = true
                    binding.btnLogin?.isEnabled = false
                }
                is AuthViewModel.AuthState.Success -> {
                    binding.progressBar?.isVisible = false
                    binding.btnLogin?.isEnabled = true

                    if (state.message == "Login successful") {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is AuthViewModel.AuthState.Error -> {
                    binding.progressBar?.isVisible = false
                    binding.btnLogin?.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.currentUser.observe(this) { user ->
            if (user != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin?.setOnClickListener {
            val email = binding.etEmail?.text.toString().trim()
            val password = binding.etPassword?.text.toString().trim()

            viewModel.login(email, password)
        }

        binding.tvRegister?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
