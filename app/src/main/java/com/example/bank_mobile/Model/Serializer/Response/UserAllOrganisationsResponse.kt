package com.example.bank_mobile.Model.Serializer.Response
import kotlinx.serialization.Serializable

@Serializable
data class UserAllOrganisationsResponse(
    var id: Int,
    var orgName: String,
    var legalAddress: String,
    var genDirector: String,
    var foundingDate: String
)
