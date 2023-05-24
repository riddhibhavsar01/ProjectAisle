package com.aisle.ui.common.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiRequest(
    @Json(name = "number")
    var number: String? = null,
    @Json(name = "otp")
    var otp: String? = null,
)
