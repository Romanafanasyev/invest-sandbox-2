package com.example.investsandbox2.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiServiceUnitTest {

    // Mock objects for testing
    private val mockLoginRequest = LoginRequest("test_user", "password")
    private val mockRegistrationRequest = RegistrationRequest("test_user", "password")
    private val mockUserId = 123

    private val apiService: ApiService = MockApiService()

    @Test
    fun login_isCorrect() = runBlocking {
        val authResponse = apiService.login(mockLoginRequest)
        assertEquals(123, authResponse.user_id) // Assuming successful login
        assertEquals(null, authResponse.error) // Assuming no error occurred
    }

    @Test
    fun register_isCorrect() = runBlocking {
        val authResponse = apiService.register(mockRegistrationRequest)
        assertEquals(123, authResponse.user_id) // Assuming successful registration
        assertEquals(null, authResponse.error) // Assuming no error occurred
    }

    @Test
    fun getUserInfo_isCorrect() = runBlocking {
        val userInfoResponse = apiService.getUserInfo(mockUserId)
        assertEquals(123, userInfoResponse.user_id) // Assuming valid user info retrieved
        assertEquals(null, userInfoResponse.error) // Assuming no error occurred
    }

    @Test
    fun getUserStocks_isCorrect() = runBlocking {
        val userStocks = apiService.getUserStocks(mockUserId)
        assertEquals(2, userStocks.size) // Assuming user has 2 stocks
    }

    @Test
    fun getAllStocks_isCorrect() = runBlocking {
        val allStocks = apiService.getAllStocks()
        assertEquals(0, allStocks.size) // Assuming there are no stocks in total
    }

    @Test
    fun buyStock_isCorrect() = runBlocking {
        val buyStockRequest = BuyStockRequest(mockUserId, 1, 10) // Assuming user wants to buy 10 stocks of ID 1
        val buySellResponse = apiService.buyStock(buyStockRequest)
        assertEquals("Stock bought successfully", buySellResponse.message) // Assuming successful purchase
        assertEquals(null, buySellResponse.error) // Assuming no error occurred
    }

    @Test
    fun sellStock_isCorrect() = runBlocking {
        val sellStockRequest = SellStockRequest(mockUserId, 1, 5) // Assuming user wants to sell 5 stocks of ID 1
        val buySellResponse = apiService.sellStock(sellStockRequest)
        assertEquals("Stock sold successfully", buySellResponse.message) // Assuming successful sale
        assertEquals(null, buySellResponse.error) // Assuming no error occurred
    }


}
