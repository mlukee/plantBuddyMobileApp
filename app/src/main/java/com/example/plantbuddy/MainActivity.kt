package com.example.plantbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lib.Plant
import com.example.plantbuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var plantFragment: PlantFragment
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MyApplication

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, PlantFragment())
            .commit()

        binding.fabAdd.setOnClickListener {
           supportFragmentManager.beginTransaction()
               .replace(R.id.fragment, InputFragment())
               .addToBackStack(null)
               .commit()
        }

        supportFragmentManager.setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            val result = bundle.getString("inputKey")
            val plant = Plant(result.toString())
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