package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionDTO (
    val success: Boolean? = null,
    @Json(name = "session_id")
    val sessionID: String? = null
)