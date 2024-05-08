package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.MoviesDTO

data class PopularMoviesState(
    val isLoading: Boolean = false,
    val popularMovies: MoviesDTO? = null,
    val error: String = ""
)