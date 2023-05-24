package com.aisle.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisle.data.local.pref.Preference
import com.aisle.data.remote.Api
import com.aisle.data.remote.ApiResponse
import com.aisle.ui.common.model.ApiRequest
import com.aisle.ui.common.model.Results
import com.aisle.utils.listener.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: Api,
) : ViewModel() {

    private val _apiState = SingleLiveEvent<ApiResponse<Results>>()
    val apiState: LiveData<ApiResponse<Results>>
        get() = _apiState


   // API calling for mobile login
    fun sendMobileLogin(mobileNumber : String,isRefresh: Boolean = false, isLoadMore: Boolean = false) {
        _apiState.value = ApiResponse.Loading(isRefresh, isLoadMore)

        viewModelScope.launch {
            _apiState.value = api.sendMobileNumber(ApiRequest(number = mobileNumber))
        }
    }
}