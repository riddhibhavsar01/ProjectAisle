package com.aisle.data.remote

import com.aisle.BuildConfig
import com.aisle.ui.common.model.ApiRequest
import com.aisle.ui.common.model.NotesData
import com.aisle.ui.common.model.Results
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager : Api {


    private val apiService: ApiService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return@lazy Retrofit.Builder()
            .baseUrl("https://app.aisle.co/")
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    override suspend fun sendMobileNumber(request: ApiRequest): ApiResponse<Results> {
        return executeApiHelper { apiService.sendMobileNumber(request) }
    }

    override suspend fun verifyOtp(request: ApiRequest): ApiResponse<Results> {
        return executeApiHelper { apiService.verifyOtp(request) }
    }

    override suspend fun getUserProfiles(token : String): ApiResponse<NotesData> {
        return executeApiHelper { apiService.getUserProfiles(token) }
    }

}



