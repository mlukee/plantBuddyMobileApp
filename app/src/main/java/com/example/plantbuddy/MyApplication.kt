package com.example.plantbuddy

import android.app.Application
import com.example.lib.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

const val FILE_NAME = "plants.json"
class MyApplication: Application() {
    var plants: MutableList<Plant> = mutableListOf()
    private val file: File by lazy { File(filesDir, FILE_NAME) }
    private val gson= Gson()

    override fun onCreate() {
        super.onCreate()
        loadPlants()
    }

    private fun savePlants() {
        val jsonString = gson.toJson(plants)
        file.writeText(jsonString)
    }

    private fun loadPlants() {
        if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<List<Plant>>() {}.type
            plants = gson.fromJson(jsonString, type)
        }
    }

    fun addPlant(plant: Plant) {
        plants.add(plant)
        savePlants()
    }

    fun removePlantById(id: String) {
        plants.removeIf { it.id == id }
        savePlants()
    }

    fun removePlantByPosition(position: Int) {
        plants.removeAt(position)
        savePlants()
    }

    fun updatePlant(plant: Plant) {
        val index = plants.indexOfFirst { it.id == plant.id }
        if (index != -1) {
            plants[index] = plant
            savePlants()
        }
    }

    fun getPlantPosition(plant: Plant): Int {
        return plants.indexOfFirst { it.id == plant.id }
    }

    fun findPlantById(id: String): Plant? {
        return plants.find { it.id == id }
    }


    fun loadMarkers(): String? {
        val file = File(filesDir, "stores_in_ptuj.json")
        val json = file.readText(Charsets.UTF_8)
        return json
    }

    fun saveFile(){
        val json ="{\n" +
                "  \"stores\": [\n" +
                "    {\n" +
                "      \"imgSrc\": \"domkoptuj\",\n" +
                "      \"name\": \"Domko Ptuj - Trgovina Z Opremo Za Dom\",\n" +
                "      \"address\": \"Vegova ulica 2h, 2250 Ptuj, Slovenia\",\n" +
                "      \"working_hours\": \"Monday: 7:00 AM - 4:00 PM\\nTuesday: 7:00 AM - 4:00 PM\\nWednesday: 7:00 AM - 4:00 PM\\nThursday: 7:00 AM - 4:00 PM\\nFriday: 7:00 AM - 4:00 PM\\nSaturday: Closed\\nSunday: Closed\",\n" +
                "      \"lat\": \"46.420600\",\n" +
                "      \"lng\": \"15.878840\",\n" +
                "      \"contact\": \"+386 2 787 73 00\",\n" +
                "      \"website\": \"https://domko-ptuj.business.site/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"imgSrc\": \"eldar\",\n" +
                "      \"name\": \"El-Dar Trgovina\",\n" +
                "      \"address\": \"Nova vas pri Ptuju 53, 2250 Ptuj, Slovenia\",\n" +
                "      \"working_hours\": \"Monday: 8:00 AM - 7:00 PM\\nTuesday: 8:00 AM - 7:00 PM\\nWednesday: 8:00 AM - 7:00 PM\\nThursday: 8:00 AM - 7:00 PM\\nFriday: 8:00 AM - 7:00 PM\\nSaturday: 8:00 AM - 7:00 PM\\nSunday: Closed\",\n" +
                "      \"contact\": \"+386 (02) 780 02 01\",\n" +
                "      \"email\": \"dar@dar-ptuj.si\",\n" +
                "      \"lat\": \"46.437740\",\n" +
                "      \"lng\": \"15.888080\",\n" +
                "      \"website\": \"www.eldar.si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"imgSrc\": \"vrnicenterjanko\",\n" +
                "      \"name\": \"Vrtni center Ptuj, JANKO KELENC S.P.\",\n" +
                "      \"address\": \"Bukovci 51, Bukovci, 2281 Markovci\",\n" +
                "      \"working_hours\": \"Monday: 8:00 AM - 4:00 PM\\nTuesday: 8:00 AM - 4:00 PM\\nWednesday: 8:00 AM - 4:00 PM\\nThursday: 8:00 AM - 4:00 PM\\nFriday: 8:00 AM - 4:00 PM\\nSaturday: 8:00 AM - 1:00 PM\\nSunday: Closed\",\n" +
                "      \"contact\": \"00 386 041 562 793\",\n" +
                "      \"email\": \"janko.kelenc@gmail.com\",\n" +
                "      \"lat\": \"46.387960\",\n" +
                "      \"lng\": \"15.960590\",\n" +
                "      \"website\": \"https://vrt-kelenc.si/vrtni-center-ptuj/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"imgSrc\": \"obiptuj\",\n" +
                "      \"name\": \"OBI Ptuj\",\n" +
                "      \"address\": \"Puhova ulica 19, 2250 Ptuj\",\n" +
                "      \"working_hours\": \"Monday: 8:00 AM - 4:00 PM\\nTuesday: 8:00 AM - 4:00 PM\\nWednesday: 8:00 AM - 4:00 PM\\nThursday: 8:00 AM - 4:00 PM\\nFriday: 8:00 AM - 4:00 PM\\nSaturday: Closed\\nSunday: Closed\",\n" +
                "      \"contact\": \"+386 2 799 06 92\",\n" +
                "      \"email\": \"trgovinasi778@obi.si\",\n" +
                "      \"lat\": \"46.422330\",\n" +
                "      \"lng\": \"15.885610\",\n" +
                "      \"website\": \"http://www.obi.si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"imgSrc\": \"zivexptuj\",\n" +
                "      \"name\": \"Živex Trgovina Ptuj\",\n" +
                "      \"address\": \"Mariborska cesta 3, 2250 Ptuj, Slovenia\",\n" +
                "      \"working_hours\": \"Monday: 8:00 AM - 5:00 PM\\nTuesday: 8:00 AM - 5:00 PM\\nWednesday: 8:00 AM - 5:00 PM\\nThursday: 8:00 AM - 5:00 PM\\nFriday: 8:00 AM - 5:00 PM\\nSaturday: 8:00 AM - 1:00 PM\\nSunday: Closed\",\n" +
                "      \"contact\": \"+386 2 771 16 77\",\n" +
                "      \"email\": \"zivex.ptuj@zivex.si\",\n" +
                "      \"lat\": \"46.415460\",\n" +
                "      \"lng\": \"15.863160\",\n" +
                "      \"website\": \"https://www.zivex.si\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n"
        val file = File(filesDir, "stores_in_ptuj.json")
        file.writeText(json)
    }

}