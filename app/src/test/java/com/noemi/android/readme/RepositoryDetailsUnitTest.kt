package com.noemi.android.readme

import com.noemi.android.readme.data.RepositoryDetails
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
class RepositoryDetailsUnitTest {

    private val repoDetails = RepositoryDetails(
        12, "leakcanary",
        "A memory detection library for Android.", 999, 369, 639,
        6639, 3
    )

    @Test
    fun repoDetailsShouldPass() {
        val expectedDetails = RepositoryDetails(
            12, "leakcanary",
            "A memory detection library for Android.", 999, 369, 639,
            6639, 3
        )
        assertEquals(repoDetails, expectedDetails)
    }

    @Test
    fun repoDetailsShouldFail() {
        val expectedDetails = RepositoryDetails(
            9, "leakcanary",
            "A memory detection library for Android.", 999, 369, 639,
            6639, 3
        )
        assertNotEquals(repoDetails, expectedDetails)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}