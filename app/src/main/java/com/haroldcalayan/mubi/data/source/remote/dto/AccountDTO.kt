package com.haroldcalayan.mubi.data.source.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountDTO(
    val avatar: Avatar? = null,
    val id: Int? = null,

    @Json(name = "iso_639_1")
    val iso639_1: String? = null,

    @Json(name = "iso_3166_1")
    val iso3166_1: String? = null,

    val name: String? = null,

    @Json(name = "include_adult")
    val includeAdult: Boolean? = null,

    val username: String? = null
)

@JsonClass(generateAdapter = true)
data class Avatar (
    val gravatar: Gravatar? = null,
    val tmdb: Tmdb? = null
)

@JsonClass(generateAdapter = true)
data class Gravatar (
    val hash: String? = null
)

@JsonClass(generateAdapter = true)
data class Tmdb (
    @Json(name = "avatar_path")
    val avatarPath: Any? = null
)

