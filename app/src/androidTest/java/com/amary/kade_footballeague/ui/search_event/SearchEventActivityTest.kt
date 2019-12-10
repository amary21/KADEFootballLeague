package com.amary.kade_footballeague.ui.search_event

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.amary.kade_footballeague.R.id.mn_app_search
import com.amary.kade_footballeague.R.id.rvSearch
import com.amary.kade_footballeague.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchEventActivityTest{

    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchEventActivity::class.java)

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)

    }

    @Test
    fun testAppBehaviour() {
        onView(withId(mn_app_search))
            .check(matches(isDisplayed()))
        onView(withId(mn_app_search))
            .perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("man united"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(4000)

        onView(withId(rvSearch)).check(matches(isDisplayed()))



    }
}