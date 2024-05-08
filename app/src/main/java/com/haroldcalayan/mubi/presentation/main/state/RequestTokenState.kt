package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.RequestTokenDTO

data class RequestTokenState(
    val isLoading: Boolean = false,
    val data: RequestTokenDTO? = null,
    val error: String = ""
)