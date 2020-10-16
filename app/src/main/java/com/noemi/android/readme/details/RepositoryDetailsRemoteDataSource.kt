package com.noemi.android.readme.details

import androidx.lifecycle.LiveData
import com.noemi.android.readme.data.RepositoryDetails
import com.noemi.android.readme.data.RepositoryReadMe
import com.noemi.android.readme.helper.SingleLiveData

interface RepositoryDetailsRemoteDataSource {

    fun getRemoteRepositoryDetails(): LiveData<RepositoryDetails>

    fun getReposReadme(): LiveData<RepositoryReadMe>

    val detailsFailureError: SingleLiveData<Any>

    val readmeFailureError: SingleLiveData<Any>
}