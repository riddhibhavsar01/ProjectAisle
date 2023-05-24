package com.aisle.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aisle.data.local.pref.Preference
import com.aisle.data.remote.Api
import com.aisle.data.remote.ApiResponse
import com.aisle.ui.common.model.NotesData
import com.aisle.utils.listener.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: Api,
    private val preference: Preference
) : ViewModel() {

    private val _apiState = SingleLiveEvent<ApiResponse<NotesData>>()
    val apiState: LiveData<ApiResponse<NotesData>>
        get() = _apiState
    private var page = 1
    private var job: Job? = null

    fun getUserProfiles(isRefresh: Boolean = false, isLoadMore: Boolean = false) {
        _apiState.value = ApiResponse.Loading(isRefresh, isLoadMore)
        if (isLoadMore) page++ else page = 1
        job = viewModelScope.launch {
            _apiState.value = api.getUserProfiles(preference.getToken())
        }
    }
}