package com.example.investsandbox2.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthorizationActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AuthorizationActivity::class.java)

    @Test
    fun loginButtonDisplayed() {
        val tagValue = "loginButton"
        val viewWithTagVI = onView(withTagValue(`is`(tagValue as Any)))
        viewWithTagVI.check(matches(isDisplayed()))
    }

    @Test
    fun signUpButtonDisplayed() {
        onView(withTagValue(`is`("signUpButton"))).check(matches(isDisplayed()))
    }

    @Test
    fun loginFlow() {
        // Enter username and password
        onView(withTagValue(`is`("usernameField"))).perform(replaceText("username"))
        onView(withTagValue(`is`("passwordField"))).perform(replaceText("password"), closeSoftKeyboard())

        // Click the login button
        onView(withTagValue(`is`("loginButton"))).perform(click())

        // Check if StockActivity is displayed
        onView(withTagValue(`is`("stockActivity"))).check(matches(isDisplayed()))
    }

    @Test
    fun signUpFlow() {
        // Click "Don't have an account yet?" button
        onView(withTagValue(`is`("signUpLink"))).perform(click())

        // Enter username and password
        onView(withTagValue(`is`("usernameField"))).perform(replaceText("username"))
        onView(withTagValue(`is`("passwordField"))).perform(replaceText("password"), closeSoftKeyboard())

        // Click the sign up button
        onView(withTagValue(`is`("signUpButton"))).perform(click())

        // Check if StockActivity is displayed
        onView(withTagValue(`is`("stockActivity"))).check(matches(isDisplayed()))
    }
}