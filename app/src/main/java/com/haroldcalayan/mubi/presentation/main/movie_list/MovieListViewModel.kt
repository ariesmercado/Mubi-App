package com.haroldcalayan.mubi.presentation.main.movie_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.common.base.BaseViewModel
import com.haroldcalayan.mubi.domain.use_case.GetAiringTodayUseCase
import com.haroldcalayan.mubi.domain.use_case.GetOnTvMoviesUseCase
import com.haroldcalayan.mubi.domain.use_case.GetPopularMovieUseCase
import com.haroldcalayan.mubi.domain.use_case.GetTopRatedMoviesUseCase
import com.haroldcalayan.mubi.presentation.main.state.PopularMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMovieUseCase: GetPopularMovieUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getAiringTodayUseCase: GetAiringTodayUseCase,
    private val getOnTvMoviesUseCase: GetOnTvMoviesUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(PopularMoviesState())
    val state: State<PopularMoviesState> = _state

    init {
        getTopRatedMovies()
    }

    fun getPopularMovies() {
        getPopularMovieUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = PopularMoviesState(popularMovies = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAiringToday() {
        getAiringTodayUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = PopularMoviesState(popularMovies = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getOnTvMovies() {
        getOnTvMoviesUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = PopularMoviesState(popularMovies = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getTopRatedMovies() {
        getTopRatedMoviesUseCase().onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = PopularMoviesState(popularMovies = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}