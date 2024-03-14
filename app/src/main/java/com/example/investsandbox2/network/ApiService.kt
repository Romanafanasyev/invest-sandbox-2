package com.example.investsandbox2.network

import com.example.investsandbox2.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body credentials: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body credentials: RegistrationRequest): AuthResponse

}

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegistrationRequest(
    val username: String,
    val password: String
)
