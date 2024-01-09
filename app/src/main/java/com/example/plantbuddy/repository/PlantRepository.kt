package com.example.plantbuddy.repository

import androidx.lifecycle.LiveData
import com.example.plantbuddy.data.PlantDao
import com.example.plantbuddy.model.Plant

class PlantRepository(private val plantDao: PlantDao) {

    val readAllData: LiveData<List<Plant>> = plantDao.readAllData()

    suspend fun addPlant(plant: Plant) {
        plantDao.addPlant(plant)
    }

    suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    suspend fun deletePlant(plant: Plant) {
        plantDao.deletePlant(plant)
    }

    suspend fun deleteAllPlants() {
        plantDao.deleteAllPlants()
    }
}