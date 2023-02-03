package com.example.bank_mobile.Model.Interface.IRepository

import com.example.bank_mobile.Model.Repository.UserRepository
import com.example.bank_mobile.Model.Serializer.Request.*
import com.example.bank_mobile.Model.Serializer.Response.*
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

    suspend fun verificateUser(userVerificationRequest: UserVerificationRequest): UserVerificationResponse?

    suspend fun getProfileUser(): UserProfileResponse?

    suspend fun getAllOrganisations(): List<UserAllOrganisationsResponse>?

    suspend fun getOnlineServices(userOnlineServicesRequest: UserOnlineServicesRequest): List<UserOnlineServiceResponse>?

    suspend fun takeLoanOnline(userTakeLoanOnlineRequest: UserTakeLoanOnlineRequest): UserTakeLoanOnlineResponse?

    suspend fun getAllNotification(userNotificationRequest: UserNotificationRequest): List<UserNotificationResponse>?

    companion object {
        private var user: IUserRepository? = null
        fun create() : IUserRepository {
            if (user == null) {
                user = UserRepository(
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
            return user!!
        }
    }
}