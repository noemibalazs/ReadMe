package com.noemi.android.readme.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noemi.android.readme.data.RepositoryDetails
import com.noemi.android.readme.data.RepositoryReadMe
import com.noemi.android.readme.helper.DataManager
import com.noemi.android.readme.helper.SingleLiveData
import com.noemi.android.readme.network.GitHubApiService
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsRemoteDataSourceImpl(
    private val gitHubApiService: GitHubApiService,
    private val dataManager: DataManager
) : RepositoryDetailsRemoteDataSource {

    val detailsError = SingleLiveData<Any>()
    val readmeError = SingleLiveData<Any>()

    override fun getRemoteRepositoryDetails(): LiveData<RepositoryDetails> {
        val mutableRepoDetails = MutableLiveData<RepositoryDetails>()
        GlobalScope.launch(Dispatchers.IO) {
            val result = gitHubApiService.getRepositoryDetails(dataManager.getRepositoryId())
            withContext(Dispatchers.Main) {
                try {
                    result.enqueue(object : Callback<RepositoryDetails> {
                        override fun onResponse(
                            call: Call<RepositoryDetails>,
                            response: Response<RepositoryDetails>
                        ) {
                            if (!response.isSuccessful) {
                                detailsError.call()
                                Logger.e(
                                    KOIN_TAG,
                                    "onResponse reposDetails failure, see code: ${response.code()}"
                                )
                            }

                            if (response.isSuccessful) {
                                mutableRepoDetails.value = response.body()!!
                                Logger.d(KOIN_TAG, "onResponse reposDetails is successful!")
                            }
                        }

                        override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {
                            detailsError.call()
                            Logger.e(KOIN_TAG, "onFailure reposDetails message: ${t.message}")
                        }

                    })

                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "getRemoteRepositoryDetails throws exception: ${e.message}")
                }
            }
        }

        return mutableRepoDetails
    }

    override fun getReposReadme(): LiveData<RepositoryReadMe> {
        val mutableReadme = MutableLiveData<RepositoryReadMe>()
        GlobalScope.launch(Dispatchers.IO) {
            val fullName = dataManager.getRepositoryFullName().split("/")
            val response = gitHubApiService.getRepositoryReadMe(fullName[0], fullName[1])
            withContext(Dispatchers.Main) {
                try {
                    response.enqueue(object : Callback<RepositoryReadMe> {
                        override fun onResponse(
                            call: Call<RepositoryReadMe>,
                            response: Response<RepositoryReadMe>
                        ) {

                            if (!response.isSuccessful) {
                                readmeError.call()
                                Logger.e(
                                    KOIN_TAG,
                                    "onResponse readMe failure, see code: ${response.code()}"
                                )
                            }

                            if (response.isSuccessful) {
                                mutableReadme.value = response.body()!!
                                Logger.d(KOIN_TAG, "onResponse readMe is successful")
                            }
                        }

                        override fun onFailure(call: Call<RepositoryReadMe>, t: Throwable) {
                            readmeError.call()
                            Logger.e(KOIN_TAG, "onFailure readme error:${t.message} ")
                        }

                    })

                } catch (e: Exception) {
                    Logger.e(KOIN_TAG, "getReposReadme throws exception: ${e.message}")
                }
            }
        }

        return mutableReadme
    }

    override val detailsFailureError: SingleLiveData<Any>
        get() = detailsError

    override val readmeFailureError: SingleLiveData<Any>
        get() = readmeError
}