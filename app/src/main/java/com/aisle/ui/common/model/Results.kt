package com.aisle.ui.common.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "status")
    var status: Boolean? = null,
    @Json(name = "token")
    var token: String? = null
)