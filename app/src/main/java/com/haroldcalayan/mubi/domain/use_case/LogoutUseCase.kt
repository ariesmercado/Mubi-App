package com.haroldcalayan.mubi.domain.use_case

import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.data.repository.AuthenticationRepository
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.source.remote.dto.SessionDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading())
            movieRepository.clearLocalData()
            authenticationRepository.clearSession()
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}