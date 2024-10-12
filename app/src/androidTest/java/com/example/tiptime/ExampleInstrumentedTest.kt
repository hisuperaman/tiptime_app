package com.example.tiptime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiptime.ui.theme.TipTimeTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.text.NumberFormat

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()
//
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.tiptime", appContext.packageName)
//    }

    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.setContent {
            TipTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipTimeLayout(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }

        composeTestRule.onNodeWithText(appContext.getString(R.string.bill_amount)).performTextInput("100.0")
        composeTestRule.onNodeWithText(appContext.getString(R.string.how_was_the_service)).performTextInput("20.0")
        composeTestRule.onNodeWithText("Tip Amount: ${NumberFormat.getCurrencyInstance().format(20.0)}").assertExists("No node found with specified text")
    }
}