package com.example.bank_mobile.Model.Serializer.Request
import kotlinx.serialization.Serializable

@Serializable
data class UserOnlineServicesRequest(
    var orgId: Int
)
