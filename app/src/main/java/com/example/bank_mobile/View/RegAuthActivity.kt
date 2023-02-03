package com.example.bank_mobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.bank_mobile.Model.HTTPInfo
import com.example.bank_mobile.Model.Interface.IRepository.IUserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserRegAuthRequest
import com.example.bank_mobile.databinding.ActivityRegAuthBinding
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegAuthBinding
    var userRepository = IUserRepository.create()

    val checkTokenUserData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val checkAuthRegUserData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val saveTokensData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveTokensData.value = 0
        val saveTokensObserver = Observer<Int> {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
                Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("accessToken", HTTPInfo.accessToken)
            editor.putString("refreshToken", HTTPInfo.refreshToken)
            editor.apply()
        }
        saveTokensData.observe(this@RegAuthActivity, saveTokensObserver)

        //goToNextScreen()
        checkTokens()

        binding.btnContinue.setOnClickListener {
            var login = binding.regLogin.text.toString()
            var pass =  binding.regPassword.text.toString()
            tryAuth(login, pass)
        }
    }

    fun checkTokens() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)

        if (sharedPreferences.getString("accessToken", "") == "") {
            return
        }

        checkTokenUserData.value = 0
        val checkTokenUserObserver = Observer<Int> {
            if (checkTokenUserData.value == 2) {
                goToNextScreen()
            } else if (checkTokenUserData.value == 1) {
                checkRefreshToken()
            }
        }
        checkTokenUserData.observe(this@RegAuthActivity, checkTokenUserObserver)
        CoroutineScope(Dispatchers.IO).launch {
            var inval: Int
            if (userRepository.checkTokenUser()!!.isSuccess) {
                inval = 2
            } else {
                inval = 1
            }

            checkTokenUserData.postValue(inval)
        }
    }

    fun checkRefreshToken() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)

        if (sharedPreferences.getString("refreshToken", "") == "") {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.updateTokenUser()!!
            var inval: Int
            if (res.refresh_token != "") {
                HTTPInfo.accessToken = res.access_token
                HTTPInfo.refreshToken = res.refresh_token
                saveTokensData.postValue(saveTokensData.value!! + 1)
                inval = 2
            } else {
                inval = 0
            }

            checkTokenUserData.postValue(inval)
        }
    }

    fun goToNextScreen() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)
        Intent(this, MainActivity::class.java).also { startActivity(it) }
        this.finish()
    }

    fun tryAuth(login: String, pass: String) {
        checkAuthRegUserData.value = 0
        val checkAuthRegUserObserver = Observer<Int> {
            if (checkAuthRegUserData.value == 2) {
                goToNextScreen()
            } else if (checkAuthRegUserData.value == 1) {
                tryReg((fun() = login)(), (fun() = pass)())
            }
        }
        checkAuthRegUserData.observe(this@RegAuthActivity, checkAuthRegUserObserver)
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.authorizeUser(UserRegAuthRequest(login, pass))
            var inval: Int
            if (res != null) {
                HTTPInfo.accessToken = res.access_token
                HTTPInfo.refreshToken = res.refresh_token
                saveTokensData.postValue(saveTokensData.value!! + 1)
                inval = 2
            } else {
                inval = 1
            }

            checkAuthRegUserData.postValue(inval)
        }
    }

    fun tryReg(login: String, pass: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.registerUser(UserRegAuthRequest(login, pass))
            var inval: Int
            if (res != null) {
                HTTPInfo.accessToken = res.access_token
                HTTPInfo.refreshToken = res.refresh_token
                saveTokensData.postValue(saveTokensData.value!! + 1)
                inval = 2
            } else {
                inval = 0
            }

            checkAuthRegUserData.postValue(inval)
        }
    }
}