package com.haroldcalayan.mubi.domain.use_case

import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSessionIDUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading())
            val data = repository.getSessionID().orEmpty()
            emit(Response.Success(data))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}