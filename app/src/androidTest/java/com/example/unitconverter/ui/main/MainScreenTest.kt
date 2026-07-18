package com.example.unitconverter.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.unitconverter.theme.UnitConverterTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/** UI tests for [MainScreen]. */
class MainScreenTest {

    @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            UnitConverterTheme { MainScreen() }
        }
    }

    @Test
    fun appTitle_isDisplayed() {
        composeTestRule.onNodeWithText("Unit Converter").assertIsDisplayed()
    }

    @Test
    fun categoryChips_areDisplayed() {
        composeTestRule.onNodeWithText("Length").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight").assertIsDisplayed()
        composeTestRule.onNodeWithText("Temperature").assertIsDisplayed()
        composeTestRule.onNodeWithText("Volume").assertIsDisplayed()
    }
}
