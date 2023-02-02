package com.example.bank_mobile.View

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
import com.example.bank_mobile.databinding.ActivityProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    var userRepository = IUserRepository.create()
    val saveTokensData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val handler = Handler(Looper.myLooper()!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        getProfileData()
    }

    fun fillProfile(login: String, phone: String, email: String, fullName: String) {
        with(binding) {
            textLogin.setText("Login: " + login)
            textPhone.setText("Phone: " + phone)
            textEmail.setText("E-mail: " + email)
            textFullName.setText("Full name: " + fullName)
        }
    }

    fun getProfileData() {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.getProfileUser()
            if (res!!.login == "401") {
                checkRefreshToken()
                res = userRepository.getProfileUser()
            }
            handler.post {
                fillProfile(res!!.login, res!!.phone, res!!.email, res!!.fullname)
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
        saveTokensData.observe(this@ProfileActivity, saveTokensObserver)
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