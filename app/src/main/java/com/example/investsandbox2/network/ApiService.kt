package com.example.investsandbox2.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun login(@Body credentials: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body credentials: RegistrationRequest): AuthResponse
    @GET("user-info/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: Int): UserInfoResponse

    @GET("user-stocks/{userId}")
    suspend fun getUserStocks(@Path("userId") userId: Int): List<UserStockResponse>

    @GET("all-stocks")
    suspend fun getAllStocks(): List<AllStockResponse>

    @POST("buy-stock")
    suspend fun buyStock(@Body request: BuyStockRequest): BuySellResponse

    @POST("sell-stock")
    suspend fun sellStock(@Body request: SellStockRequest): BuySellResponse

}

data class BuyStockRequest(
    val user_id: Int,
    val stock_id: Int,
    val quantity: Int
)

data class SellStockRequest(
    val user_id: Int,
    val stock_id: Int,
    val quantity: Int
)

data class BuySellResponse(
    val message: String?,
    val error: String?
)

data class UserInfoResponse(
    val user_id: Int?,
    val username: String?,
    val balance: Double?,
    val error: String?
)

data class UserStockResponse(
    val stock_id: Int?,
    val name: String?,
    val price: Double?,
    val bought_quantity: Int?,
    val error: String?
)

data class AllStockResponse(
    val id: Int?,
    val name: String?,
    val price: Double?,
    val quantity: Int?,
    val error: String?
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegistrationRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val user_id: Int?,
    val error: String?
)
