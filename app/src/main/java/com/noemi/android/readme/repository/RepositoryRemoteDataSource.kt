package com.noemi.android.readme.repository

import com.noemi.android.readme.data.Repositories
import retrofit2.Call

interface RepositoryRemoteDataSource {

    fun callRepositories(name: String): Call<Repositories>
}