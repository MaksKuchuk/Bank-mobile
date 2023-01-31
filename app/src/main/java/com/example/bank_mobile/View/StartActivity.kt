package com.example.bank_mobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isNewPerson()

        binding.btnStart.setOnClickListener {
            Intent(this, RegAuthActivity::class.java).also { startActivity(it) }
            this.finish()
        }
    }

    private fun isNewPerson() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isOldPerson", false)) {
            Intent(this, RegAuthActivity::class.java).also { startActivity(it) }
            this.finish()
        }

        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isOldPerson", true)
        editor.apply()
    }
}