package com.weatherclothes.artist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weatherclothes.artist.data.dao.LocationDao
import com.weatherclothes.artist.data.models_db.LocationEntityDb

@Database(
    version = 1,
    entities = [LocationEntityDb::class]
)
abstract class AppDataBase : RoomDatabase() {

    companion object {

        private var instance: AppDataBase? = null

        @Synchronized
        fun get(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java, "app.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun locationDao(): LocationDao
}