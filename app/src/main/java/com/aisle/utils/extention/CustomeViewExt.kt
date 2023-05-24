@file:Suppress("NOTHING_TO_INLINE", "UNUSED_PARAMETER")

package com.aisle.utils.extention

import android.view.View
import com.aisle.R
import com.aisle.data.remote.ApiResponse
import com.aisle.ui.common.base.BaseAdapter
import com.aisle.widget.ApiViewStateConstraintLayout

fun <T> ApiResponse<T>?.handleApiView(
    progressLayout: ApiViewStateConstraintLayout?,
    onClickListener: View.OnClickListener? = null,
    skipIds: List<Int> = emptyList(),
    isSuccess: (t: T?) -> Unit = {}
) {
    when (this) {
        is ApiResponse.Loading -> {
            progressLayout?.showHorizontalProgress()
        }
        is ApiResponse.Success -> {
            isSuccess(data)
            progressLayout?.showContent(skipIds)
        }
        is ApiResponse.ApiError -> {
            progressLayout?.showContent(skipIds)
            progressLayout?.context?.showSnackBar(
                view = progressLayout.rootView,
                message = apiErrorMessage,
                isError = true
            )
        }
        is ApiResponse.ServerError -> {
            progressLayout?.showContent(skipIds)
            progressLayout?.context?.showSnackBar(
                view = progressLayout.rootView,
                message = errorMessage,
                isError = true
            )
        }
        is ApiResponse.NoInternetConnection -> {
            progressLayout?.showContent(skipIds)
            progressLayout?.context?.showSnackBar(
                view = progressLayout.rootView,
                message = progressLayout.getString(R.string.no_internet_message),
                isError = true
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> ApiResponse<T>?.handleListApiView(
    progressLayout: ApiViewStateConstraintLayout?,
    skipIds: List<Int> = emptyList(),
    onClickListener: View.OnClickListener? = null,
    crossinline isSuccess: (t: T?) -> Unit = {}
) {
    val adapter: BaseAdapter<T>? = progressLayout?.recyclerView?.adapter as? BaseAdapter<T>?
    when (this) {
        is ApiResponse.Loading -> {
            if (isLoadMore) {
                progressLayout?.recyclerView?.post { adapter?.addLoadMore() }
                progressLayout?.swipeRefreshLayout?.isEnabled = false
            } else {
                progressLayout?.swipeRefreshLayout?.isRefreshing = isRefresh
                if (!isRefresh) {
                    progressLayout?.showProgress()
                    progressLayout?.swipeRefreshLayout?.isEnabled = false
                }
            }
        }
        is ApiResponse.Success -> {
            if (isRequiredClear) {
                adapter?.clearAllItems()
            }
            if (progressLayout?.swipeRefreshLayout?.isRefreshing == true)
                progressLayout.recyclerView?.post { adapter?.clearAllItems() }
            (data as? List<T?>).takeIf { it != null }?.also {
                progressLayout?.recyclerView?.post {
                    adapter?.removeLoadMore()
                    adapter?.addAll(it)
                    if ((adapter?.itemCount ?: 0) == 0) {
                        progressLayout.showError(
                            R.drawable.ic_no_record,
                            message = successMessage ?: progressLayout.getString(R.string.no_record_found),
                            buttonTextResId = R.string.button_refresh,
                            onClickListener = onClickListener
                        )
                    } else progressLayout.showContent(skipIds)
                }
            } ?: let {
                progressLayout?.recyclerView?.post {
                    adapter?.removeLoadMore(true)
                    data?.let { isSuccess(it) }
                    if ((adapter?.itemCount ?: 0) == 0) {
                        progressLayout.showError(
                            R.drawable.ic_no_record,
                            message = successMessage ?: progressLayout.getString(R.string.no_record_found),
                            buttonTextResId = R.string.button_refresh,
                            onClickListener = onClickListener
                        )
                    } else progressLayout.showContent(skipIds)
                }
            }
            progressLayout?.scrollListener?.resetState()
            if (progressLayout?.recyclerView == null) {
                data?.let { isSuccess(it) }
                progressLayout?.showContent(skipIds)
            }
            progressLayout?.swipeRefreshLayout?.apply {
                isRefreshing = false
                isEnabled = true
            }
        }
        is ApiResponse.ServerError -> {
            if (progressLayout?.swipeRefreshLayout?.isRefreshing == true || (adapter?.itemCount ?: 0) > 0) {
                progressLayout?.swipeRefreshLayout?.isRefreshing = false
                progressLayout?.swipeRefreshLayout?.isEnabled = true
                progressLayout?.recyclerView?.post { adapter?.removeLoadMore(true) }
                progressLayout?.rootView?.let { progressLayout.context?.showSnackBar(view = it, message = errorMessage, isError = true) }
            } else {
                progressLayout?.showError(
                    R.drawable.ic_cloud_off_black_24dp,
                    R.string.server_error_title,
                    message = errorMessage,
                    buttonTextResId = R.string.button_try_again,
                    onClickListener = onClickListener
                )
            }
        }
        is ApiResponse.NoInternetConnection -> {
            if (progressLayout?.swipeRefreshLayout?.isRefreshing == true || (adapter?.itemCount ?: 0) > 0) {
                progressLayout?.swipeRefreshLayout?.isRefreshing = false
                progressLayout?.swipeRefreshLayout?.isEnabled = true
                progressLayout?.recyclerView?.post { adapter?.removeLoadMore(true) }
                progressLayout?.rootView?.let { progressLayout.context?.showSnackBar(view = it, messageResId = R.string.no_internet_message, isError = true) }
            } else {
                progressLayout?.showError(
                    R.drawable.ic_no_internet_24dp,
                    R.string.no_internet_title,
                    messageResId = R.string.no_internet_message,
                    buttonTextResId = R.string.button_try_again,
                    onClickListener = onClickListener,
                    isDisplayInternetSettingPanel = true
                )
            }
        }
    }
}


inline fun <T> ApiResponse<T>.whenLoading(function: () -> Unit): ApiResponse<T> {
    when (this) {
        is ApiResponse.Loading -> {
            function()
        }
    }
    return this
}

inline fun <T> ApiResponse<T>.whenSuccess(function: (T) -> Unit): ApiResponse<T> {
    when (this) {
        is ApiResponse.Success -> {
            data?.let { function(it) }
        }
    }
    return this
}

inline fun <T> ApiResponse<T>.whenFailed(function: () -> Unit): ApiResponse<T> {
    when (this) {
        is ApiResponse.Success -> {

        }
        is ApiResponse.Loading -> {

        }
        else -> {
            function()
        }
    }
    return this
}

