package com.noemi.android.readme

import com.noemi.android.readme.repository.RepositoryActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest = Config.NONE)
class RepositoryActivityUnitTest {

    private var repositoryActivity: RepositoryActivity ?= null

    @Before
    fun setUp(){
        repositoryActivity = Robolectric.buildActivity(RepositoryActivity::class.java).create().resume().get()
    }

    @Test
    fun testMainContext(){
        val context = RuntimeEnvironment.systemContext
        assertEquals("android", context.packageName)
    }

    @After
    fun tearDown(){
        stopKoin()
    }
}