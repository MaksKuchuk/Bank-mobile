package com.example.bank_mobile.Model.Domain

data class Service(
    var id: Int,
    var serviceName: String,
    var description: String,
    var percent: String,
    var minLoanPeriod: String,
    var maxLoadPeriod: String
)