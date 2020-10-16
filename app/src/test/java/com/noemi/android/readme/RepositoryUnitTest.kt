package com.noemi.android.readme

import com.noemi.android.readme.data.Repository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest = Config.NONE)
class RepositoryUnitTest {

    private val repo = Repository(12, "leakcanary", "square/leakcanary",
        "A memory detection library for Android.")

    @Test
    fun testRepositoryShouldPass(){
        val expectedRepo = Repository(12, "leakcanary", "square/leakcanary", "A memory detection library for Android.")
        assertEquals(repo, expectedRepo)
    }

    @Test
    fun testRepositoryShouldFail(){
        val expectedRepo = Repository(9, "leakcanary", "square/leakcanary", "A memory detection library for Android.")
        assertNotEquals(repo, expectedRepo)
    }

    @After
    fun tearDown(){
        stopKoin()
    }
}