package com.haroldcalayan.mubi.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.common.base.BaseViewModel
import com.haroldcalayan.mubi.domain.use_case.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel  @Inject constructor(
    private val sessionUseCase: GetSessionUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow((SessionState()))
    val state = _state.asStateFlow()

    fun getRequestToken(requestToken: String) {
        sessionUseCase(requestToken).onEach { result ->
            when (result) {
                is Response.Success -> {
                    _state.value = SessionState(data = result.data)
                }
                is Response.Error -> {
                    _state.value = SessionState(error = "Session Denied.")
                }
            }
        }.launchIn(viewModelScope)
    }

}