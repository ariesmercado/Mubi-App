package com.haroldcalayan.mubi.presentation.main.state

import com.haroldcalayan.mubi.data.source.remote.dto.AccountDTO

data class AccountState(
    val isLoading: Boolean = false,
    val data: AccountDTO? = null,
    val error: String = ""
)
