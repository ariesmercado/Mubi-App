package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.MovieDetailsDTO

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: MovieDetailsDTO? = null,
    val error: String = ""
)