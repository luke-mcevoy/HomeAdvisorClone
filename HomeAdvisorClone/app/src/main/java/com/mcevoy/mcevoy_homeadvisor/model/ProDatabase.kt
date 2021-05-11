package com.mcevoy.mcevoy_homeadvisor.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ProData::class), version=1)
abstract class ProDatabase: RoomDatabase() {
    abstract fun proDao(): ProDao

    companion object {
        @Volatile private var instance: ProDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProDatabase::class.java,
            "prodatabase"
        ).build()
    }
}