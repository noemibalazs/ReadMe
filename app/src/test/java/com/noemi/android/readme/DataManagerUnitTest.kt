package com.noemi.android.readme

import com.noemi.android.readme.helper.DataManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest = Config.NONE)
class DataManagerUnitTest {

    private var dataManager: DataManager? = null
    private var repositoryID = 777
    private var repoName = "Oz/Wizard"

    @Before
    fun createDataManager() {
        dataManager = DataManager(RuntimeEnvironment.application.applicationContext)
    }

    @Test
    fun repositoryIdShouldPassedTest() {
        dataManager?.saveRepositoryId(repositoryID)
        val idResult = dataManager?.getRepositoryId()
        assertEquals(repositoryID, idResult)
    }

    @Test
    fun repositoryIdShouldFailTest() {
        dataManager?.saveRepositoryId(repositoryID)
        val idResult = dataManager?.getRepositoryId()
        assertNotEquals(696, idResult)
    }

    @Test
    fun repositoryFullNameShouldPassTest(){
        dataManager?.saveRepositoryFullName(repoName)
        val nameResult = dataManager?.getRepositoryFullName()
        assertEquals(repoName, nameResult)
    }

    @Test
    fun repositoryFullNameShouldFailTest(){
        dataManager?.saveRepositoryFullName(repoName)
        val nameResult = dataManager?.getRepositoryFullName()
        assertNotEquals("Kotlin/Brain", nameResult)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}