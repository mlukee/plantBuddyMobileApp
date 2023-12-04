package com.example.plantbuddy

import PlantManager
import android.app.Application
import com.example.lib.Plant
import java.io.File
import java.util.UUID

const val FILE_NAME = "plants.json"
class MyApplication: Application() {
//    var plants: PlantManager = PlantManager()
    var plants: MutableList<Plant> = mutableListOf()
    lateinit var file: File

    override fun onCreate() {
        super.onCreate()
//        plants = PlantManager()
//        file = File(filesDir, FILE_NAME)
//        initData()
    }

    fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    // Remove a plant by its UUID
    fun removePlantById(id: String) {
        plants.removeIf { it.id == id }
    }

    fun removePlantByPosition(position: Int) {
        plants.removeAt(position)
    }

    // Get a list of all plants
    fun getAllPlants(): List<Plant> {
        return plants.toList()
    }

    fun updatePlant(plant: Plant){
        val index = plants.indexOfFirst { it.id == plant.id }
        if (index != -1) {
            plants[index].name = plant.name
            plants[index].plantType = plant.plantType
            plants[index].wateringSchedule = plant.wateringSchedule
        }
    }

    fun getPlantPosition(plant: Plant): Int {
        return plants.indexOfFirst { it.id == plant.id }
    }

    // Find a plant by its UUID
    fun findPlantById(id: String): Plant? {
        return plants.find { it.id == id }
    }
}