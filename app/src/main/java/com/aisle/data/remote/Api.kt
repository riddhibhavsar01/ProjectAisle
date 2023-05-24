package com.aisle.data.remote

import com.aisle.ui.common.model.ApiRequest
import com.aisle.ui.common.model.NotesData
import com.aisle.ui.common.model.Results

interface Api {

    suspend fun sendMobileNumber(request: ApiRequest): ApiResponse<Results>
    suspend fun verifyOtp(request: ApiRequest): ApiResponse<Results>
    suspend fun getUserProfiles(token : String): ApiResponse<NotesData>
}
