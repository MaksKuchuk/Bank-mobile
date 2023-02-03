package com.example.bank_mobile.View

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bank_mobile.Model.Domain.Notification
import com.example.bank_mobile.Model.HTTPInfo
import com.example.bank_mobile.Model.Interface.IRepository.IUserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserNotificationRequest
import com.example.bank_mobile.Model.Serializer.Response.UserNotificationResponse
import com.example.bank_mobile.ViewModel.NotificationAdapter
import com.example.bank_mobile.databinding.ActivityListOfPlacesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOfPlacesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListOfPlacesBinding
    var userRepository = IUserRepository.create()
    val saveTokensData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val handler = Handler(Looper.myLooper()!!)

    private val notifAdapter = NotificationAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOfPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNotifAdapter()
        setObserver()
        getPhone()
    }

    fun initNotifAdapter() {
        binding.notificationList.layoutManager = LinearLayoutManager(this@ListOfPlacesActivity)
        binding.notificationList.adapter = notifAdapter
    }

    fun getPhone() {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.getProfileUser()
            if (res!!.login == "401") {
                checkRefreshToken()
                res = userRepository.getProfileUser()
            }
            handler.post {
                if (res != null && res!!.phone != "") {
                    getNotification(res!!.phone)
                }
            }
        }
    }

    fun getNotification(phone: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.getAllNotification(UserNotificationRequest(phone))
            handler.post {
                if (res != null) {
                    drawNotification(res!!)
                }
            }
        }
    }

    fun drawNotification(res: List<UserNotificationResponse>) {
        notifAdapter.notificationList.clear()
        notifAdapter.notificationList.addAll(res.map {
            Notification(
                it.notificationId,
                it.userId,
                it.description,
                it.status
            )
        })
        notifAdapter.notifyDataSetChanged()
    }

    fun setObserver() {
        saveTokensData.value = 0
        val saveTokensObserver = Observer<Int> {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(
                "infoFile", MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("accessToken", HTTPInfo.accessToken)
            editor.putString("refreshToken", HTTPInfo.refreshToken)
            editor.apply()
        }
        saveTokensData.observe(this@ListOfPlacesActivity, saveTokensObserver)
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