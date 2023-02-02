package com.example.bank_mobile.Model.Serializer.Response
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    var login: String,
    var phone: String,
    var email: String,
    var fullname: String
)
