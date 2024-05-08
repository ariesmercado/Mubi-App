package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenDTO (
    val success: Boolean? = null,

    @Json(name = "expires_at")
    val expiresAt: String? = null,

    @Json(name = "request_token")
    val requestToken: String? = null
)
