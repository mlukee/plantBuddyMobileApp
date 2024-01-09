package com.example.plantbuddy.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.plantbuddy.model.Plant

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlant(plant: Plant)

    @Update
    fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAllPlants()

    @Query("SELECT * FROM plant_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Plant>>

}