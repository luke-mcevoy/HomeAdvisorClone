package com.mcevoy.mcevoy_homeadvisor.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProDao {
    @Insert
    suspend fun insertAll(vararg pros: ProData): List<Long>

    @Query(value = "SELECT * FROM prodata")
    suspend fun getAllPros(): List<ProData>

    @Query(value = "SELECT * FROM prodata WHERE uuid = :proId")
    suspend fun getPro(proId: Int): ProData

    @Query(value = "DELETE FROM prodata")
    suspend fun deleteAllPros()
}