package com.example.bank_mobile.Model.Serializer.Request
import kotlinx.serialization.Serializable

@Serializable
data class UserTakeLoanOnlineRequest(
    var serviceId: Int,
    var amount: Int,
    var period: Int
)
