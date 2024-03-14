package com.example.investsandbox2.network

import kotlinx.coroutines.runBlocking

class MockApiService : ApiService {
    override suspend fun login(credentials: LoginRequest): AuthResponse {
        // Simulating a successful login response
        return AuthResponse(123, null)
    }

    override suspend fun register(credentials: RegistrationRequest): AuthResponse {
        // Simulating a successful registration response
        return AuthResponse(123, null)
    }

    override suspend fun getUserInfo(userId: Int): UserInfoResponse {
        // Simulating a successful user info response
        return UserInfoResponse(123, "test_user", 1000.0, null)
    }

    override suspend fun getUserStocks(userId: Int): List<UserStockResponse> {
        // Simulating a response with two user stocks
        val stock1 = UserStockResponse(1, "Stock A", 10.0, 20, null)
        val stock2 = UserStockResponse(2, "Stock B", 20.0, 30, null)
        return listOf(stock1, stock2)
    }


    override suspend fun getAllStocks(): List<AllStockResponse> {
        // Simulating a successful response with empty stock list
        return emptyList()
    }

    override suspend fun buyStock(request: BuyStockRequest): BuySellResponse {
        // Simulating a successful buy stock response
        return BuySellResponse("Stock bought successfully", null)
    }

    override suspend fun sellStock(request: SellStockRequest): BuySellResponse {
        // Simulating a successful sell stock response
        return BuySellResponse("Stock sold successfully", null)
    }
}
