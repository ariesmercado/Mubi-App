package com.haroldcalayan.mubi.data.repository

import com.haroldcalayan.mubi.common.Constants
import com.haroldcalayan.mubi.common.base.BaseRepository
import com.haroldcalayan.mubi.data.source.local.pref.PreferenceManager
import com.haroldcalayan.mubi.data.source.local.pref.PreferenceManager.Companion.get
import com.haroldcalayan.mubi.data.source.local.pref.PreferenceManager.Companion.set
import com.haroldcalayan.mubi.data.source.remote.TMDBApi
import com.haroldcalayan.mubi.data.source.remote.dto.AccountDTO
import com.haroldcalayan.mubi.data.source.remote.dto.RequestTokenDTO
import com.haroldcalayan.mubi.data.source.remote.dto.SessionDTO
import okhttp3.RequestBody

interface AuthenticationRepository {
    suspend fun getRequestToken(): RequestTokenDTO
    suspend fun getSession(request: RequestBody): SessionDTO
    suspend fun getSessionID(): String?
    suspend fun getAccount(sessionId: String): AccountDTO
    suspend fun clearSession()
}

class AuthenticationRepositoryImpl(
    private val api: TMDBApi,
    private val pref: PreferenceManager
) : BaseRepository(), AuthenticationRepository {

    override suspend fun getRequestToken() = api.getRequestToken()

    override suspend fun getSession(request: RequestBody): SessionDTO {
        val response = api.getSession(requestBody = request)
        pref.pref()[Constants.PREF_KEY_SESSION_ID] = response.sessionID
        return response
    }

    override suspend fun getSessionID(): String? {
        return pref.pref()[Constants.PREF_KEY_SESSION_ID]
    }

    override suspend fun getAccount(sessiontId: String) = api.getAccount(sessionId = sessiontId)

    override suspend fun clearSession() {
        pref.pref().edit().apply {
            clear()
            apply()
        }
    }

}