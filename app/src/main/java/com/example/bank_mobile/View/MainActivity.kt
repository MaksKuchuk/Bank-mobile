package com.example.bank_mobile.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnListOfPlaces.setOnClickListener {
                Intent(this@MainActivity, ListOfPlacesActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnLoanOnline.setOnClickListener {
                Intent(this@MainActivity, LoanOnlineActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnVerification.setOnClickListener {
                Intent(this@MainActivity, VerificationActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnMyProfile.setOnClickListener {
                Intent(this@MainActivity, ProfileActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnShowMap.setOnClickListener {
                Intent(this@MainActivity, ShowMapActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnFAQ.setOnClickListener {
                Intent(this@MainActivity, HelpActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}