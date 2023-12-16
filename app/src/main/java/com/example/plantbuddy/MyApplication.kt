package com.example.plantbuddy

import android.app.Application
import com.example.lib.Plant
import java.io.File

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


    fun loadMarkers(): String? {
        //open file and read it
        val file = File(filesDir, "stores_in_ptuj.json")
        val json = file.readText(Charsets.UTF_8)
        return json
    }

    fun saveFile(){
        val json ="{\n" +
                "  \"stores\": [\n" +
                "    {\n" +
                "      \"name\": \"Domko Ptuj - Trgovina Z Opremo Za Dom\",\n" +
                "      \"address\": \"Vegova ulica 2h, 2250 Ptuj, Slovenia\",\n" +
                "      \"working_hours\": \"Monday to Friday, 7:00 AM \\u2013 4:00 PM; Closed on Saturdays and Sundays\",\n" +
                "      \"lat\": \"46.420600\",\n" +
                "      \"lng\": \"15.878840\",\n" +
                "      \"contact\": \"+386 2 787 73 00\",\n" +
                "      \"website\": \"https://domko-ptuj.business.site/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"El-Dar Trgovina\",\n" +
                "      \"address\": \"Nova vas pri Ptuju 53, 2250 Ptuj, Slovenia\",\n" +
                "      \"contact\": \"+386 (02) 780 02 01\",\n" +
                "      \"email\": \"dar@dar-ptuj.si\",\n" +
                "      \"lat\": \"46.437740\",\n" +
                "      \"lng\": \"15.888080\",\n" +
                "      \"website\": \"www.eldar.si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Vrtni center Ptuj, JANKO KELENC S.P.\",\n" +
                "      \"address\": \"Bukovci 51, Bukovci, 2281 Markovci\",\n" +
                "      \"contact\": \"00 386 041 562 793\",\n" +
                "      \"email\": \"janko.kelenc@gmail.com\",\n" +
                "      \"lat\": \"46.387960\",\n" +
                "      \"lng\": \"15.960590\",\n" +
                "      \"website\": \"https://vrt-kelenc.si/vrtni-center-ptuj/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"OBI Ptuj\",\n" +
                "      \"address\": \"Puhova ulica 19, 2250 Ptuj\",\n" +
                "      \"working_hours\": \"Open daily from 8:00 AM to 8:00 PM\",\n" +
                "      \"contact\": \"+386 2 799 06 92\",\n" +
                "      \"email\": \"trgovinasi778@obi.si\",\n" +
                "      \"lat\": \"46.422330\",\n" +
                "      \"lng\": \"15.885610\",\n" +
                "      \"website\": \"http://www.obi.si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"\\u017divex Trgovina Ptuj\",\n" +
                "      \"address\": \"Mariborska cesta 3, 2250 Ptuj, Slovenia\",\n" +
                "      \"working_hours\": \"Monday to Friday, 8:00 AM - 6:00 PM; Saturday, 8:00 AM - 1:00 PM; Closed on Sunday\",\n" +
                "      \"contact\": \"+386 2 771 16 77\",\n" +
                "      \"email\": \"zivex.ptuj@zivex.si\",\n" +
                "      \"lat\": \"46.415460\",\n" +
                "      \"lng\": \"15.863160\",\n" +
                "      \"website\": \"https://www.zivex.si\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        val file = File(filesDir, "stores_in_ptuj.json")
        file.writeText(json)
    }

}