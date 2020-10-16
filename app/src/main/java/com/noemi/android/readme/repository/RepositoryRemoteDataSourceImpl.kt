package com.noemi.android.readme.repository

import com.noemi.android.readme.data.Repositories
import com.noemi.android.readme.network.GitHubApiService
import com.orhanobut.logger.Logger
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call

class RepositoryRemoteDataSourceImpl(
    private val gitHubApiService: GitHubApiService,
) : RepositoryRemoteDataSource {

    override fun callRepositories(name: String): Call<Repositories> {
        Logger.d(KOIN_TAG, "callRepositories")
        return gitHubApiService.getRepositoryList(name)
    }
}