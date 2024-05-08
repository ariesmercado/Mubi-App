package com.haroldcalayan.mubi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.haroldcalayan.mubi.common.base.BaseDao
import com.haroldcalayan.mubi.data.source.local.entity.FavoriteTVShow

@Dao
interface FavoriteTVShowDao : BaseDao<FavoriteTVShow> {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun all(): List<FavoriteTVShow>?

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: Int): FavoriteTVShow?

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): FavoriteTVShow?

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun nukeTable()

    companion object {
        const val TABLE_NAME = "favoritetvshow"
    }
}