package com.aisle.data.remote

import com.aisle.ui.common.model.ApiRequest
import com.aisle.ui.common.model.NotesData
import com.aisle.ui.common.model.Results
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("V1/users/phone_number_login")
    suspend fun sendMobileNumber(@Body mobileNumber: ApiRequest?): Response<Results>

    @POST("V1/users/verify_otp")
    suspend fun verifyOtp(@Body since: ApiRequest): Response<Results>

    @GET("V1/users/test_profile_list")
    suspend fun getUserProfiles(@Header("Authorization") token : String): Response<NotesData>

}
