package com.noemi.android.readme

import com.noemi.android.readme.data.RepositoryReadMe
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
class RepositoryReadMeUnitTest {

    private val readMe = RepositoryReadMe(content = "Try to decode me...")

    @Test
    fun testReadMeShouldPass(){
        val expectedReadme = RepositoryReadMe(content = "Try to decode me...")
        assertEquals(readMe, expectedReadme)
    }

    @Test
    fun testReadMeShouldFail(){
        val expectedReadme = RepositoryReadMe(content = "Try to decode me... using Base64")
        assertNotEquals(readMe, expectedReadme)
    }


    @After
    fun tearDown(){
        stopKoin()
    }
}