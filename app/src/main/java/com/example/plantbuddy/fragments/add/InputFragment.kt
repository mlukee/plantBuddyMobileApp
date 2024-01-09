package com.example.plantbuddy.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.plantbuddy.R
import com.example.plantbuddy.model.Plant
import com.example.plantbuddy.viewmodel.PlantViewModel
import com.example.plantbuddy.databinding.FragmentInputBinding
import java.util.Calendar


enum class ARGUMENTS(val key: String) {
    PLANT_NAME("plantName"),
    PLANT_TYPE("plantType"),
    PLANT_ID("plantID"),
    PLANT_SCHEDULE("plantSchedule"), // Added this for consistency
    PLANT_LAST_WATERING("plantLastWatering") // Added this for consistency
}

class InputFragment : Fragment(R.layout.fragment_input) {
    private lateinit var binding: FragmentInputBinding
    private var plantID: String? = null
    private lateinit var mPlantViewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        mPlantViewModel = ViewModelProvider(this).get(PlantViewModel::class.java)

        setupSpinner()

        binding.addPlant.setOnClickListener {
            if (!inputCheck(
                    binding.inputNameText.editText?.text.toString(),
                    binding.plantTypeSpinner?.selectedItem.toString(),
                    binding.inputScheduleText?.editText?.text.toString()
                )
            ) {
                Toast.makeText(
                    requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT
                ).show()
            } else {
                val minute = binding.timePicker?.minute
                val hour = binding.timePicker?.hour
                val day = binding.datePicker?.dayOfMonth
                val month = binding.datePicker?.month
                val year = binding.datePicker?.year
                val calendar = Calendar.getInstance()
                var timeInMillis = 0L
                if (minute != null && hour != null && day != null && month != null && year != null) {
                    calendar.set(year, month, day, hour, minute)
                    timeInMillis = calendar.timeInMillis
                } else {
                    timeInMillis = System.currentTimeMillis()
                }
                val plant = Plant(0, binding.inputNameText.editText?.text.toString(),
                    binding.plantTypeSpinner?.selectedItem.toString(),
                    binding.inputScheduleText?.editText?.text.toString(),
                    timeInMillis)

                mPlantViewModel.addPlant(plant)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            }

            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.plant_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.plantTypeSpinner?.adapter = adapter
        }

        binding.plantTypeSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedPlantType = parent.getItemAtPosition(position).toString()
                    updateWateringSchedule(selectedPlantType)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun inputCheck(name: String, plantType: String, schedule: String): Boolean {
        return !(name == "" || plantType == "" || schedule == "")
    }

    private fun updateWateringSchedule(plantType: String) {
        val schedule = when (plantType) {
            "Cactus", "Succulent", "Bamboo", "Aloe Vera",
            "Snake Plant", "Ficus", "Bonsai", "Palm",
            "Jade Plant", "Sunflower" -> "Every 7-10 days"

            "Orchid", "Spider Plant", "Peace Lily", "Rubber Plant",
            "Pothos", "Money Tree", "Calathea", "Monstera",
            "Daisy", "Hydrangea" -> "Every 5-7 days"

            "Fern", "Rose", "Lilac" -> "Every 2-3 days"
            "Begonia", "Zinnia", "Marigold" -> "Every 3-5 days"
            "Philodendron", "Lavender" -> "Every 2-3 weeks"
            "Tulip" -> "Every 7 days"
            else -> "Default schedule"
        }
        binding.inputScheduleText?.editText?.setText(schedule)
    }
}
