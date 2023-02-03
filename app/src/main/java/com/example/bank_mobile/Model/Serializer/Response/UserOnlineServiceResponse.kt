package com.example.bank_mobile.Model.Serializer.Response
import kotlinx.serialization.Serializable

@Serializable
data class UserOnlineServiceResponse(
    var id: Int,
    var serviceName: String,
    var description: String,
    var percent: String,
    var minLoanPeriod: String,
    var maxLoadPeriod: String
)
