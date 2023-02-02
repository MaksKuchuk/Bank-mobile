package com.example.bank_mobile.Model.Repository

import com.example.bank_mobile.Model.HTTPInfo
import com.example.bank_mobile.Model.HttpRoutes
import com.example.bank_mobile.Model.Interface.IRepository.IUserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserRegAuthRequest
import com.example.bank_mobile.Model.Serializer.Request.UserVerificationRequest
import com.example.bank_mobile.Model.Serializer.Response.UserProfileResponse
import com.example.bank_mobile.Model.Serializer.Response.UserRegAuthIsGoodTokenResponse
import com.example.bank_mobile.Model.Serializer.Response.UserRegAuthResponse
import com.example.bank_mobile.Model.Serializer.Response.UserVerificationResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class UserRepository(
    private val client: HttpClient
) : IUserRepository {
    override suspend fun registerUser(userRegAuthRequest: UserRegAuthRequest): UserRegAuthResponse? {
        return try {client.post<UserRegAuthResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.accessToken)
            }
            url(HttpRoutes.REGISTER_USER)
            contentType(ContentType.Application.Json)
            body = userRegAuthRequest
        }} catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun authorizeUser(userRegAuthRequest: UserRegAuthRequest): UserRegAuthResponse? {
        return try {client.post<UserRegAuthResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.accessToken)
            }
            url(HttpRoutes.AUTHORIZE_USER)
            contentType(ContentType.Application.Json)
            body = userRegAuthRequest
        }} catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun checkTokenUser(): UserRegAuthIsGoodTokenResponse? {
        return try {client.post<UserRegAuthIsGoodTokenResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.accessToken)
            }
            url(HttpRoutes.VALIDATETOKEN_USER)
            contentType(ContentType.Application.Json)
        }} catch (e: Exception) {
            println("Error: ${e.message}")
            UserRegAuthIsGoodTokenResponse(false)
        }
    }

    override suspend fun updateTokenUser(): UserRegAuthResponse? {
        return try {client.get<UserRegAuthResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.refreshToken)
            }
            url(HttpRoutes.REFRESHTOKEN_USER)
            contentType(ContentType.Application.Json)
        }} catch (e: Exception) {
            println("Error: ${e.message}")
            UserRegAuthResponse("", "")
        }
    }

    override suspend fun verificateUser(userVerificationRequest: UserVerificationRequest): UserVerificationResponse? {
        return try {client.post<UserVerificationResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.accessToken)
            }
            url(HttpRoutes.VERIFICATION_USER)
            contentType(ContentType.Application.Json)
            body = userVerificationRequest
        }} catch (e: ClientRequestException) {
            UserVerificationResponse(e.response.status.value)
        } catch (e: Exception) {
            println("Error: ${e.message}")
            UserVerificationResponse(0)
        }
    }

    override suspend fun getProfileUser(): UserProfileResponse? {
        return try {client.get<UserProfileResponse> {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + HTTPInfo.accessToken)
            }
            url(HttpRoutes.GETPROFILEDATA_USER)
            contentType(ContentType.Application.Json)
        }} catch (e: ClientRequestException) {
            UserProfileResponse(e.response.status.value.toString(), "", "", "")
        } catch (e: Exception) {
            println("Error: ${e.message}")
            UserProfileResponse("", "", "", "")
        }
    }


}