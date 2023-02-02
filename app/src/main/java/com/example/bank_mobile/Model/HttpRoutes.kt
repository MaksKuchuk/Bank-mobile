package com.example.bank_mobile.Model

object HttpRoutes {
    private const val BASE_URL = "http://194.67.97.8"

    const val REGISTER_USER = "$BASE_URL/api/v1/user/registration"
    const val AUTHORIZE_USER = "$BASE_URL/api/v1/user/authorization"
    const val VALIDATETOKEN_USER = "$BASE_URL/api/v1/token/validate"
    const val REFRESHTOKEN_USER = "$BASE_URL/api/v1/token/refresh_token"
}