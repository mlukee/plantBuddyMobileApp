package com.example.plantbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lib.Plant
import com.example.plantbuddy.databinding.ActivityMainBinding
import java.util.UUID




class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var plantFragment: PlantFragment? = null
    lateinit var app: MyApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MyApplication

        plantFragment = supportFragmentManager.findFragmentById(R.id.fragment) as PlantFragment?

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, PlantFragment())
            .commit()

        binding.fabAdd.setOnClickListener {
           supportFragmentManager.beginTransaction()
               .replace(R.id.fragment, InputFragment())
               .addToBackStack(null)
               .commit()
        }

        supportFragmentManager.setFragmentResultListener("editRequestKey", this) { requestKey, bundle ->
            val idString = bundle.getString("id")
            val id = idString?.let { UUID.fromString(it) }
            val name = bundle.getString("name")
            val plant = Plant(name!!, id!!)
            app.plants.updatePlant(plant)
            plantFragment?.updatePlants(app.plants.getPlantPosition(plant))
            Toast.makeText(this, "Plant ${plant.name} updated", Toast.LENGTH_SHORT).show()
        }

        supportFragmentManager.setFragmentResultListener("addRequestKey", this) { requestKey, bundle ->
            val idString = bundle.getString("id")
            val id = idString?.let { UUID.fromString(it) }
            val name = bundle.getString("name")
            val plant = Plant(name!!, id!!)
            app.plants.addPlant(plant)
            Toast.makeText(this, "Plant ${plant.name} added", Toast.LENGTH_SHORT).show()
        }



        binding.fabHome.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, PlantFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}