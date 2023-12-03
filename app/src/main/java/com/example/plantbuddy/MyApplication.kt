package com.example.plantbuddy

import PlantManager
import android.app.Application
import java.io.File

const val FILE_NAME = "plants.json"
class MyApplication: Application() {
    var plants: PlantManager = PlantManager()
    lateinit var file: File

    override fun onCreate() {
        super.onCreate()
        plants = PlantManager()
//        file = File(filesDir, FILE_NAME)
//        initData()
    }
}