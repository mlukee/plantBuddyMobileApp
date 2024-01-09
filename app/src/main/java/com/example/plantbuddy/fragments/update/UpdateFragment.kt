package com.example.plantbuddy.fragments.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantbuddy.R
import com.example.plantbuddy.databinding.FragmentUpdateBinding
import com.example.plantbuddy.model.Plant
import com.example.plantbuddy.viewmodel.PlantViewModel
import java.util.Calendar

class UpdateFragment : Fragment() {


    private lateinit var binding: FragmentUpdateBinding

    private lateinit var mPlantViewModel: PlantViewModel
    private var plantID = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        val bundle = arguments
        val plant = bundle?.let { it.getParcelable<Plant>("plant_key") }

        setupSpinner()


        // Check if the plant is not null before accessing its properties
        plant?.let {
            // Now, you can access the properties of the Plant object and set them in your fragment
            this.binding.editName.setText(it.name)
            val plantTypeIndex = getPlantTypeIndex(it.plantType)
            binding.editplantTypeSpinner.setSelection(plantTypeIndex)
            binding.editSchedule.setText(it.wateringSchedule)

            plantID = it.id

            val lastWatering = it.lastWatering
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastWatering

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            binding.editdatePicker.init(year, month, day, null)
            binding.edittimePicker.hour = hour
            binding.edittimePicker.minute = minute

            binding.editPlant.setOnClickListener{
                updateItem()
                parentFragmentManager.popBackStack()

            }

        }


        return binding.root
    }

    private fun updateItem() {
        val name = binding.editName.text.toString()
        val plantType = binding.editplantTypeSpinner.selectedItem.toString()
        val wateringSchedule = binding.editSchedule.text.toString()
        val lastWatering = System.currentTimeMillis()

        if (inputCheck(name, plantType, wateringSchedule)) {
            val updatedPlant =
                Plant(plantID, name, plantType, wateringSchedule, lastWatering)
            mPlantViewModel.updatePlant(updatedPlant)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()


        }else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(name: String, plantType: String, wateringSchedule: String): Boolean {
        return !(name.isEmpty() || plantType.isEmpty() || wateringSchedule.isEmpty())
    }

    private fun getPlantTypeIndex(plantType: String): Int {
        val plantTypes = resources.getStringArray(R.array.plant_types)
        for (i in plantTypes.indices) {
            if (plantTypes[i] == plantType) {
                return i
            }
        }
        return 0
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.plant_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editplantTypeSpinner?.adapter = adapter
        }

        binding.editplantTypeSpinner?.onItemSelectedListener =
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
        binding.editSchedule?.setText(schedule)
    }
}