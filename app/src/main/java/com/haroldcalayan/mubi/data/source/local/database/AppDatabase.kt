package com.haroldcalayan.mubi.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haroldcalayan.mubi.data.source.local.dao.FavoriteTVShowDao
import com.haroldcalayan.mubi.data.source.local.entity.FavoriteTVShow

private const val VERSION_NUMBER = 1

@Database(
    entities = [FavoriteTVShow::class],
    version = VERSION_NUMBER
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteTVShowDao(): FavoriteTVShowDao
}