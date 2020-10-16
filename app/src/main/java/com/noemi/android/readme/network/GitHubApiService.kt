package com.noemi.android.readme.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.noemi.android.readme.data.*
import com.noemi.android.readme.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/repositories?sort=starts&per_page=50")
    fun getRepositoryList(@Query("q") queryParameter: String): Call<Repositories>

    @GET("repositories/{id}")
    fun getRepositoryDetails(@Path("id") id: Int): Call<RepositoryDetails>

    @GET("repos/{name}/{repo}/readme")
    fun getRepositoryReadMe(
        @Path("name") name: String,
        @Path("repo") repo: String
    ): Call<RepositoryReadMe>

    companion object {

        fun getGitHubApiService(): GitHubApiService {
            val interceptor =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client).build()
                .create(GitHubApiService::class.java)
        }
    }
}