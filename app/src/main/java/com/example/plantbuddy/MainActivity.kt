package com.example.plantbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, PlantFragment())
            .commit()

        binding.buttonAdd.setOnClickListener {
            hideFirstPlantCardView()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, InputFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.fabAdd.setOnClickListener {
            hideFirstPlantCardView()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, InputFragment())
                .addToBackStack(null)
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(
            "editRequestKey",
            this
        ) { requestKey, bundle ->
            val id = bundle.getString("plantID")
            val name = bundle.getString("name")
            val plant = Plant(name!!, id!!)
            app.updatePlant(plant)
            plantFragment?.updatePlants(app.getPlantPosition(plant))
        }

        supportFragmentManager.setFragmentResultListener(
            "addRequestKey",
            this
        ) { requestKey, bundle ->
            val name = bundle.getString("name")
            val plant = Plant(name!!)
            app.addPlant(plant)
            hideFirstPlantCardView()
        }

        binding.fabHome.setOnClickListener {
            if (plantFragment == null) {
                plantFragment = PlantFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, plantFragment!!)
                .addToBackStack(null)
                .commit()
            if(app.getAllPlants().isEmpty()) {
                binding.cardViewAddFirstPlant.visibility = View.VISIBLE
                binding.buttonAdd.visibility = View.VISIBLE
            }
        }
    }

    private fun hideFirstPlantCardView() {
        if(app.getAllPlants().isNotEmpty()) {
            return
        }
        binding.cardViewAddFirstPlant.visibility = View.GONE
        binding.buttonAdd.visibility = View.GONE
    }
}