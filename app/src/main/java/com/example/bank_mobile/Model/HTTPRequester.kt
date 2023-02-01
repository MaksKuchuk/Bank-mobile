package com.example.bank_mobile.Model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import io.ktor.client.*


class HTTPRequester : Activity() {
    companion object {
        var client = HttpClient()
        var accessToken: String = ""
        var refreshToken: String = ""
    }

    fun setTokens(aToken: String, rToken: String) {
        accessToken = aToken
        refreshToken = rToken
    }

    fun checkToken(): Boolean {


        return true
    }

    fun updateToken() {

        //TODO: update Tokens
        // accessToken =
        // refreshToken =


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("infoFile",
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.putString("refreshToken", refreshToken)
        editor.apply()
    }

    fun makeGetRequest() {

    }

    fun makePostRequest() {

    }

}