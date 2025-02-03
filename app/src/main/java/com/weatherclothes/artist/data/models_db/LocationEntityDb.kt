package com.weatherclothes.artist.data.models_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Location")
data class LocationEntityDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo (name = "name") val name: String,
    @ColumnInfo (name = "region") val region: String,
    @ColumnInfo (name = "country") val country: String,
    @ColumnInfo (name = "lat") val lat: Float,
    @ColumnInfo (name = "lon") val lon: Float,
)