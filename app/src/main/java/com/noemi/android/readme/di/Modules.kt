package com.noemi.android.readme.di

import androidx.lifecycle.SavedStateHandle
import com.noemi.android.readme.details.RepositoryDetailsRemoteDataSource
import com.noemi.android.readme.details.RepositoryDetailsRemoteDataSourceImpl
import com.noemi.android.readme.details.RepositoryDetailsViewModel
import com.noemi.android.readme.helper.DataManager
import com.noemi.android.readme.network.GitHubApiService
import com.noemi.android.readme.repository.RepositoryRemoteDataSource
import com.noemi.android.readme.repository.RepositoryRemoteDataSourceImpl
import com.noemi.android.readme.repository.RepositoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataManagerModule = module {
    single { DataManager(androidContext().applicationContext) }
}

val gitHubAPIModule = module {
    single { GitHubApiService.getGitHubApiService() }
}

val repositoryViewModelModule = module {

    single<RepositoryRemoteDataSource> {
        RepositoryRemoteDataSourceImpl(
            gitHubApiService = get()
        )
    }

    viewModel {
        RepositoryViewModel(
            remoteDataSource = get()
        )
    }
}

val repositoryDetailsViewModelModule = module {
    single<RepositoryDetailsRemoteDataSource> {
        RepositoryDetailsRemoteDataSourceImpl(
            gitHubApiService = get(),
            dataManager = get()
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        RepositoryDetailsViewModel(
            handle = handle,
            repositoryDetailsRemoteDataSource = get()
        )
    }
}