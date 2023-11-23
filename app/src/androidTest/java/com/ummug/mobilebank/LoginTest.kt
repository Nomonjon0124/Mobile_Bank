package com.ummug.mobilebank

import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.ummug.mobilebank.screen.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest :TestCase(){
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginKaspressoTest() {
        run {
            step("Check visibility") {
                LoginScreen {
                    usernameEditText.isDisplayed()
                    usernameErrorText.isNotDisplayed()
                    passwordEditText.isDisplayed()
                    passwordErrorText.isNotDisplayed()
                    login.isDisplayed()
                    goToRegister.isDisplayed()
                }
            }
            step("Check errors") {
                LoginScreen {
                    login.click()
                    usernameErrorText {
                        isDisplayed()
                        containsText("Username incorrect")
                    }
                    passwordErrorText {
                        isDisplayed()
                        containsText("Password incorrect")
                    }
                }
            }
            step("Fill fields and login") {
                LoginScreen {
                    usernameEditText.typeText("SamandAR")
                    passwordEditText.typeText("Password")
                    closeSoftKeyboard()
                    login.click()
                }
//                Home {
//                    welcomeText {
//                        isDisplayed()
//                        containsText("Welcome, Ism")
//                    }
//                }
            }
        }
    }
}