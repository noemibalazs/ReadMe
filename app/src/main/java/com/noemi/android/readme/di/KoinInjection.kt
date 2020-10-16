package com.noemi.android.readme.di

import org.koin.core.module.Module

class KoinInjection {

    companion object {

        fun injectModules(): MutableList<Module> {
            fun getDataManagerModule() = listOf(dataManagerModule)
            fun getGitHubApiModule() = listOf(gitHubAPIModule)
            fun getRepositoryVMModule() = listOf(repositoryViewModelModule)
            fun getRepositoryDetailsVMModule() = listOf(repositoryDetailsViewModelModule)

            return mutableListOf<Module>().apply {
                addAll(getDataManagerModule())
                addAll(getGitHubApiModule())
                addAll(getRepositoryVMModule())
                addAll(getRepositoryDetailsVMModule())
            }
        }
    }
}