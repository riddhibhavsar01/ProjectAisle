package com.aisle.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisle.data.remote.Api
import com.aisle.data.remote.ApiResponse
import com.aisle.ui.common.model.ApiRequest
import com.aisle.ui.common.model.Results
import com.aisle.utils.listener.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _apiState = SingleLiveEvent<ApiResponse<Results>>()
    val apiState: LiveData<ApiResponse<Results>>
        get() = _apiState

    private var job: Job? = null

    fun verifyOtp(mobileNumer : String,otp: String,isRefresh: Boolean = false, isLoadMore: Boolean = false) {
        _apiState.value = ApiResponse.Loading(isRefresh, isLoadMore)

        viewModelScope.launch {
            _apiState.value = api.verifyOtp(ApiRequest(number = mobileNumer, otp = otp))
        }
    }
}