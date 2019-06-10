package com.ferdyrodriguez.loquiero

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ferdyrodriguez.loquiero.usecases.registration.RegistrationActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RegistrationActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<RegistrationActivity>(RegistrationActivity::class.java)

    @Test
    fun registerButtonNotVisible_emailNotGoodFormatInput(){
        onView(withId(R.id.email)).perform(typeText("wrongFormat"))
        onView(withId(R.id.registrationBtn)).check(matches(not(isDisplayed())))
    }

    @Test
    fun registerButtonNotVisible_passwordDontMatch(){
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.confirmPassword)).perform(typeText("12345"))
        closeSoftKeyboard()
        onView(withId(R.id.registrationBtn)).check(matches(not(isDisplayed())))
    }

    @Test
    fun registerButtonNotVisible_passwordLessThan6Characters(){
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123"))
        closeSoftKeyboard()
        onView(withId(R.id.registrationBtn)).check(matches(not(isDisplayed())))
    }

    @Test
    fun registerButtonVisible_inputValid(){
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.confirmPassword)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.registrationBtn)).check(matches(isDisplayed()))
    }

    @Test
    fun registerButtonClick_showToast(){
        val activity = rule.activity
        onView(withId(R.id.email)).perform(typeText("email@test.com"))
        closeSoftKeyboard()
        onView(withId(R.id.password)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.confirmPassword)).perform(typeText("123456"))
        closeSoftKeyboard()
        onView(withId(R.id.registrationBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.registrationBtn)).perform(click())
        onView(withText(R.string.problem_try_again)).
            inRoot(withDecorView(not(`is`(activity.window.decorView)))).
            check(matches(isDisplayed()))
    }
}