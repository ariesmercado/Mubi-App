package com.haroldcalayan.mubi.presentation.episode

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.common.base.BaseViewModel
import com.haroldcalayan.mubi.domain.use_case.GetSeasonDetailsUseCase
import com.haroldcalayan.mubi.presentation.main.state.EpisodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel@Inject constructor(
    private val seasonDetailsUseCase: GetSeasonDetailsUseCase,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _state = mutableStateOf(EpisodeState())
    val state: State<EpisodeState> = _state

    init {
        val tvId = savedStateHandle.get<String>("tvId")
        val seasonNumber = savedStateHandle.get<String>("seasonNumber")

        getSeasonDetail(tvId.toString(), seasonNumber.toString())
    }

    private fun getSeasonDetail(tvId: String, seasonNumber: String) {
        seasonDetailsUseCase(tvId.toInt(), seasonNumber.toInt()).onEach { result ->
            when(result) {
                is Response.Success -> {
                    _state.value = EpisodeState(season = result.data)
                }
                is Response.Error -> {
                    _state.value =
                        EpisodeState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}
