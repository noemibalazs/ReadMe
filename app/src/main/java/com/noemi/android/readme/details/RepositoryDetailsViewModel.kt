package com.noemi.android.readme.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.noemi.android.readme.data.*
import com.noemi.android.readme.util.*

class RepositoryDetailsViewModel(
    val handle: SavedStateHandle,
    private val repositoryDetailsRemoteDataSource: RepositoryDetailsRemoteDataSource
) : ViewModel() {

    val showDetailsError = repositoryDetailsRemoteDataSource.detailsFailureError
    val showReadmeError = repositoryDetailsRemoteDataSource.readmeFailureError

    fun getRepositoryDetails() = repositoryDetailsRemoteDataSource.getRemoteRepositoryDetails()

    fun saveRepositoryDetailsDueConfChanges(repositoryDetails: RepositoryDetails) {
        handle.set(REPOSITORY_DETAILS_SAVED_STATE, repositoryDetails)
    }

    fun getRepositoryDetailsAfterConfChanges(): LiveData<RepositoryDetails> {
        return handle.getLiveData(REPOSITORY_DETAILS_SAVED_STATE)
    }

    fun getRepositoryReadme() = repositoryDetailsRemoteDataSource.getReposReadme()

    fun saveRepositoryReadmeDueConfChanges(readme: RepositoryReadMe) {
        handle.set(REPOSITORY_README_SAVED_STATE, readme)
    }

    fun getRepositoryReadmeAfterConfChanges(): LiveData<RepositoryReadMe> {
        return handle.getLiveData(REPOSITORY_README_SAVED_STATE)
    }
}