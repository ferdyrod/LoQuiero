package com.ferdyrodriguez.loquiero.usecases.login

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ferdyrodriguez.loquiero.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun loginButtonNotVisible_emailNotGoodFormatInput(){
        onView(withId(R.id.email)).perform(typeText("wrongFormat"))
        onView(withId(R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun loginButtonNotVisible_passwordLessThan6Characters(){
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123"))
        closeSoftKeyboard()
        onView(withId(R.id.loginButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun loginButtonVisible_inputValid(){
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.loginButton)).check(matches(isEnabled()))
    }

    @Test
    fun loginButtonClick_showErrorToast(){
        val activity = rule.activity
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.loginButton)).check(matches(isEnabled()))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.problem_try_again)).
            inRoot(withDecorView(not(`is`(activity.window.decorView)))).
            check(matches(isDisplayed()))
    }

    @Test
    fun loginButtonClick_showSuccessToast(){
        val activity = rule.activity
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.loginButton)).check(matches(isEnabled()))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Login is!!!")).
            inRoot(withDecorView(not(`is`(activity.window.decorView)))).
            check(matches(isDisplayed()))
    }
}