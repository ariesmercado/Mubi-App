package com.haroldcalayan.mubi.presentation.main.movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.common.base.BaseViewModel
import com.haroldcalayan.mubi.domain.use_case.GetMovieDetailsUseCase
import com.haroldcalayan.mubi.presentation.main.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel  @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> = _state

    init {
        savedStateHandle.get<String>("movieId")?.let { movieId ->
            getMovie(movieId)
        }
    }

    private fun getMovie(movieId: String) {
        movieDetailsUseCase(movieId.toInt()).onEach { result ->
            when(result) {
                is Response.Success -> {
                    _state.value = MovieDetailState(movie = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}