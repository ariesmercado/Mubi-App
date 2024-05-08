package com.haroldcalayan.mubi.domain.use_case

import com.haroldcalayan.mubi.common.Response
import com.haroldcalayan.mubi.data.repository.MovieRepository
import com.haroldcalayan.mubi.data.source.remote.dto.MoviesDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavoriteTVShowsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(accountId: Int, sessionId: String): Flow<Response<MoviesDTO>> = flow {
        try {
            emit(Response.Loading())
            val favoriteTVShows = repository.getFavoriteTVShows(accountId = accountId, sessionId = sessionId)
            emit(Response.Success(favoriteTVShows))
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}