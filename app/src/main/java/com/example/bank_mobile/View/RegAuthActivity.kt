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

        checkTokens()

        binding.btnContinue.setOnClickListener {
            var login = binding.regLogin.text.toString()
            var pass =  binding.regPassword.text.toString()
            getToken(login, pass)
            Intent(this, MainActivity::class.java).also { startActivity(it) }
        }
    }

    fun checkTokens() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)

        if (sharedPreferences.getString("accessToken", "") != "") {
            //TODO: check token
            Intent(this, MainActivity::class.java).also { startActivity(it) }
            this.finish()
        }

        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("accessToken", "")
        editor.putString("refreshToken", "")
        editor.apply()
    }

    fun checkToken(accessToken: String) {

    }

    fun getToken(login: String, password: String) : Pair<String, String> {
        return Pair("", "")
    }
}