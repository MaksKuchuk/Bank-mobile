package com.example.bank_mobile.Model.Serializer.Response

import kotlinx.serialization.Serializable

@Serializable
data class UserNotificationResponse(
    var notificationId: Int,
    var userId: Int,
    var description: String,
    var status: String
)
