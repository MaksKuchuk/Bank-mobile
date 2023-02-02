package com.example.bank_mobile.Model.Serializer.Response
import kotlinx.serialization.Serializable

@Serializable
data class UserRegAuthIsGoodTokenResponse(
    var isSuccess: Boolean
)
