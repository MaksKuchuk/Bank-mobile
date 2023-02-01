package com.example.bank_mobile.Model.Interface.IRepository

import com.example.bank_mobile.Model.Repository.UserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserRegAuthRequest
import com.example.bank_mobile.Model.Serializer.Response.UserRegAuthIsGoodTokenResponse
import com.example.bank_mobile.Model.Serializer.Response.UserRegAuthResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface IUserRepository {
    suspend fun registerUser(userRegAuthRequest: UserRegAuthRequest): UserRegAuthResponse?
    suspend fun authorizeUser(userRegAuthRequest: UserRegAuthRequest): UserRegAuthResponse?
    suspend fun checkTokenUser(): UserRegAuthIsGoodTokenResponse?

    suspend fun updateTokenUser(): UserRegAuthResponse?

    companion object {
        fun create() : IUserRepository {
            return UserRepository(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}