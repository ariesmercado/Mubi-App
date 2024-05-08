package com.haroldcalayan.mubi.domain.use_case

import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.source.remote.dto.RequestTokenDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(): Flow<Response<RequestTokenDTO>> = flow {
        try {
            emit(Response.Loading())
            val data = repository.getRequestToken()
            emit(Response.Success(data))
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}