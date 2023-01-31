package com.example.bank_mobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityRegAuthBinding
import com.example.bank_mobile.databinding.ActivityStartBinding

class RegAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            Intent(this, MainActivity::class.java).also { startActivity(it) }
        }
    }
}