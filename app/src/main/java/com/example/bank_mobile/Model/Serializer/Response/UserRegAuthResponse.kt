package com.example.bank_mobile.Model.Serializer.Response
import kotlinx.serialization.Serializable

@Serializable
data class UserRegAuthResponse(
    var access_token: String,
    var refresh_token: String
)
