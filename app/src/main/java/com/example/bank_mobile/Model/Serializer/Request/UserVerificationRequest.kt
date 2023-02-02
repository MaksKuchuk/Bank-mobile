package com.example.bank_mobile.Model.Serializer.Request
import kotlinx.serialization.Serializable

@Serializable
data class UserVerificationRequest(
    var phone: String,
    var email: String,
    var fullName: String
)
