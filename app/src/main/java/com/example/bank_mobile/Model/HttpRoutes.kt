package com.example.bank_mobile.Model

object HttpRoutes {
    private const val BASE_URL = "http://194.67.97.8"

    const val REGISTER_USER = "$BASE_URL/api/v1/user/registration"
    const val AUTHORIZE_USER = "$BASE_URL/api/v1/user/authorization"
    const val VALIDATETOKEN_USER = "$BASE_URL/api/v1/token/validate"
    const val REFRESHTOKEN_USER = "$BASE_URL/api/v1/token/refresh_token"

    const val VERIFICATION_USER = "$BASE_URL/api/v1/user/verification"

    const val GETPROFILEDATA_USER = "$BASE_URL/api/v1/user/getPersonalData"

    const val GETORGANISATIONS_USER = "$BASE_URL/api/v1/user/getAllOrganisations"
    const val GETONLINESERVICES_USER = "$BASE_URL/api/v1/user/getOnlineServicesByOrgId"

    const val TAKELOANONLINE_USER = "$BASE_URL/api/v1/user/takeLoanOnline"

    const val GETALLNOTIFICATION_USER = "$BASE_URL/api/v1/user/getAllNotification"
}