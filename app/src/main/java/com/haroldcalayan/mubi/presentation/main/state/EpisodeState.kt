package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.SeasonDTO

data class EpisodeState (
    val isLoading: Boolean = false,
    val season: SeasonDTO? = null,
    val error: String = ""
)
