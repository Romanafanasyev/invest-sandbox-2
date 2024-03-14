package com.example.investsandbox2.network

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Test

class RetrofitClientUnitTest {

    @Test
    fun retrofitClient_isNotNull() {
        val retrofitClient = RetrofitClient.apiService
        assertNotNull(retrofitClient) // Ensure RetrofitClient is not null
    }

    @Test
    fun retrofitClient_singletonInstance() {
        val retrofitClient1 = RetrofitClient.apiService
        val retrofitClient2 = RetrofitClient.apiService
        assertSame(retrofitClient1, retrofitClient2)
    }

}
