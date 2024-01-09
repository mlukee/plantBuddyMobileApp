package com.example.plantbuddy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.plantbuddy.data.PlantDatabase
import com.example.plantbuddy.repository.PlantRepository
import com.example.plantbuddy.model.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Plant>>
    private val repository: PlantRepository

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantDao()
        repository = PlantRepository(plantDao)
        readAllData = repository.readAllData
    }

    fun addPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlant(plant)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePlant(plant)
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlant(plant)
        }
    }

    fun deleteAllPlants() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPlants()
        }
    }
}