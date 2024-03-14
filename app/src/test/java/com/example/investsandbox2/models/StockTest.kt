package com.example.investsandbox2.models

import org.junit.Test
import org.junit.Assert.*

class StockUnitTest {

    @Test
    fun stockInitialization_isCorrect() {
        val stock = Stock(1, "Example Stock", 10.5, 100)

        assertEquals(1, stock.id)
        assertEquals("Example Stock", stock.name)
        assertEquals(10.5, stock.price, 0.0) // Delta is 0.0 for exact match
        assertEquals(100, stock.quantity)
    }

}
