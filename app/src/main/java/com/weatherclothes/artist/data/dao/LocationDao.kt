package com.weatherclothes.artist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.weatherclothes.artist.data.models_db.LocationEntityDb

@Dao
abstract class LocationDao {

    @Insert
    abstract suspend fun add(location: LocationEntityDb): Long

    @Query("SELECT * FROM Location")
    abstract suspend fun getAll(): List<LocationEntityDb>
}