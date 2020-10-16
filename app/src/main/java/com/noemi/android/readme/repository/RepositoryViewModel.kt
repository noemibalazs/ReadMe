package com.noemi.android.readme.repository

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.noemi.android.readme.data.Repositories
import com.noemi.android.readme.data.Repository
import com.noemi.android.readme.helper.OnClickEvent
import com.noemi.android.readme.helper.SingleLiveData
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.logger.KOIN_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(
    private val remoteDataSource: RepositoryRemoteDataSource
) : ViewModel() {

    val mutableRepositoryName = ObservableField("")
    val mutableRepositories = SingleLiveData<MutableList<Repository>>()

    val loading = SingleLiveData<Boolean>()
    val failureError = SingleLiveData<Any>()
    val emptyOrShortNameError = SingleLiveData<Any>()

    val searchingOK = SingleLiveData<OnClickEvent>()
    val searchingError = SingleLiveData<OnClickEvent>()

    fun onSearchClicked() {
        Logger.d(KOIN_TAG, "onSearchClicked")
        startSearching()
    }

    private fun startSearching() {
        Logger.d(KOIN_TAG, "startSearching")
        if (mutableRepositoryName.get() != null && mutableRepositoryName.get()!!.length > 3) {
            searchingOK.value = OnClickEvent.SEARCHING_OK
        } else {
            searchingError.value = OnClickEvent.SEARCHING_ERROR
        }
    }

    fun initSearching() {
        Logger.d(KOIN_TAG, "initSearching")
        loading.value = true
        GlobalScope.launch(Dispatchers.IO) {
            val result = remoteDataSource.callRepositories(mutableRepositoryName.get()!!)
            withContext(Dispatchers.Main) {
                try {
                    mutableRepositoryName.set("")
                    result.enqueue(object : Callback<Repositories> {
                        override fun onResponse(
                            call: Call<Repositories>,
                            response: Response<Repositories>
                        ) {
                            loading.value = false

                            if (!response.isSuccessful) {
                                failureError.call()
                                Log.e(KOIN_TAG, "onResponse failure, see code: ${response.code()}")
                            }

                            if (response.isSuccessful) {
                                mutableRepositories.value = response.body()?.items
                                Log.d(KOIN_TAG, "onResponse is successful!")
                            }
                        }

                        override fun onFailure(call: Call<Repositories>, t: Throwable) {
                            failureError.call()
                            loading.value = false
                            Log.e(KOIN_TAG, "onFailure see message: ${t.message}")
                        }
                    })

                } catch (e: Exception) {
                    Log.d(KOIN_TAG, "getRepositoryList throws exception: ${e.message}")
                }
            }
        }
    }

    fun searchingError() {
        Logger.d(KOIN_TAG, "searchingError")
        emptyOrShortNameError.call()
        mutableRepositoryName.set("")
    }
}