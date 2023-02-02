package com.example.bank_mobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.bank_mobile.Model.HTTPInfo
import com.example.bank_mobile.Model.Interface.IRepository.IUserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserVerificationRequest
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityVerificationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding
    var userRepository = IUserRepository.create()
    val saveTokensData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val handler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()

        binding.btnSubmit.setOnClickListener {
            with(binding) {
                var phone = editPhone.text.toString()
                var email = editEmail.text.toString()
                var fullName = editFullName.text.toString()
                verify(phone, email, fullName)
            }
        }
    }

    fun verify(phone: String, email: String, fullName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.verificateUser(UserVerificationRequest(phone, email, fullName))
            if (res!!.isSuccess == 401) {
                checkRefreshToken()
                userRepository.verificateUser(UserVerificationRequest(phone, email, fullName))
            }
        }
    }

    fun setObserver() {
        saveTokensData.value = 0
        val saveTokensObserver = Observer<Int> {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
                MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("accessToken", HTTPInfo.accessToken)
            editor.putString("refreshToken", HTTPInfo.refreshToken)
            editor.apply()
        }
        saveTokensData.observe(this@VerificationActivity, saveTokensObserver)
    }

    suspend fun checkRefreshToken() {
        var res = userRepository.updateTokenUser()!!
        if (res.refresh_token != "") {
            HTTPInfo.accessToken = res.access_token
            HTTPInfo.refreshToken = res.refresh_token
            saveTokensData.postValue(saveTokensData.value!! + 1)
        } else {
            handler.post {
                goToRegAuthActivity()
            }
        }
    }

    fun goToRegAuthActivity() {
        Intent(this, RegAuthActivity::class.java).also { startActivity(it) }
        this.finish()
    }
}
