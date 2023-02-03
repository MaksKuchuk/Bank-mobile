package com.example.bank_mobile.Model.Domain

data class Organisation (
    var id: Int,
    var orgName: String,
    var legalAddress: String,
    var genDirector: String,
    var foundingDate: String
)