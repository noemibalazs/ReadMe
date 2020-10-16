package com.noemi.android.readme

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.noemi.android.readme.data.Repository
import com.noemi.android.readme.repository.*
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryActivityInstrumentationTest {

    @get:Rule
    val rule = ActivityTestRule(RepositoryActivity::class.java)

    private lateinit var reposVM: RepositoryViewModel

    @Before
    fun setUp() {
        reposVM = mockk<RepositoryViewModel>()
    }

    @Test
    fun testAppContext() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Assert.assertEquals("com.noemi.android.readme.test", context.packageName)
    }

    @Test
    fun testSearchEditTextIsVisible() {
        onView(withId(R.id.et_search_by_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.et_search_by_name))
            .perform(typeText("kotlin"))

    }

    @Test
    fun testDoneImageIsVisible() {
        onView(withId(R.id.iv_done))
            .check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun testProgressBarIsNotVisible(){
        onView(withId(R.id.pb_repository))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun testRepositoryRV() {
        val repository = Repository(
            12, "leakcanary", "square/leakcanary",
            "A memory detection library for Android."
        )
        val repos = listOf<Repository>(repository)
        val adapter = RepositoryAdapter(reposVM)
        adapter.submitList(repos)

        rule.activity.runOnUiThread {
            rule.activity.findViewById<RecyclerView>(R.id.rv_repository).adapter = adapter
        }

        onView(withId(R.id.rv_repository))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_repository)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RepositoryVH>(
                0,
                click()
            )
        )
    }
}