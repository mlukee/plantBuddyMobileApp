import com.example.lib.Plant
import java.util.UUID

class PlantManager {
    var plants = mutableListOf<Plant>()

    // Add a new plant
    fun addPlant(plant: Plant) {
        plants.add(plant)
    }

    // Remove a plant by its UUID
    fun removePlantById(id: UUID) {
        plants.removeIf { it.id == id }
    }

    fun removePlantByPosition(position: Int) {
        plants.removeAt(position)
    }

    // Get a list of all plants
    fun getAllPlants(): List<Plant> {
        return plants.toList()
    }

    // Find a plant by its UUID
    fun findPlantById(id: UUID): Plant? {
        return plants.find { it.id == id }
    }
}
