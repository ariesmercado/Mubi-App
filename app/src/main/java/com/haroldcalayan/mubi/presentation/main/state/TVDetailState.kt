package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.TVDetailsDTO

data class TVDetailState(
    val isLoading: Boolean = false,
    val tv: TVDetailsDTO? = null,
    val error: String = ""
)