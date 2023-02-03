package com.example.bank_mobile.View

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityHelpBinding


class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setMovementMethod(ScrollingMovementMethod())
    }
}