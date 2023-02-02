package com.example.bank_mobile.Model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


class HTTPInfo : AppCompatActivity() {
    companion object {
        var accessToken: String = ""
        var refreshToken: String = ""
    }
    fun setTokens(aToken: String, rToken: String) {
        accessToken = aToken
        refreshToken = rToken
    }
}