package com.example.tiptime

import org.junit.Test

import org.junit.Assert.*
import java.text.NumberFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val amount = 100.0
        val tipPercentage = 20.0

        val expectedValue = NumberFormat.getCurrencyInstance().format(20.0)
        val actualValue = calculateTip(amount = amount, tipPercentage = tipPercentage, isRoundUp = false)

        assertEquals(expectedValue, actualValue)
    }
}