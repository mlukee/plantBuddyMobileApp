package com.example.plantbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import com.example.lib.Plant
import com.example.plantbuddy.databinding.FragmentInputBinding


enum class ARGUMENTS(val key: String) {
    PLANT_NAME("plantName"),
    PLANT_TYPE("plantType"),
    PLANT_ID("plantID"),
    PLANT_SCHEDULE("plantSchedule") // Added this for consistency
}

class InputFragment : Fragment(R.layout.fragment_input) {
    private lateinit var binding: FragmentInputBinding
    private var plantID: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        setupSpinner()

        binding.addPlant.setOnClickListener {
            if(binding.inputNameText.editText?.text.toString() == "") {
                Toast.makeText(context, "You need to input plant name!", Toast.LENGTH_SHORT).show()
            }else {
                val result = Bundle().apply {
                    putString(
                        ARGUMENTS.PLANT_NAME.key,
                        binding.inputNameText.editText?.text.toString()
                    )
                    putString(
                        ARGUMENTS.PLANT_TYPE.key,
                        binding.plantTypeSpinner?.selectedItem.toString()
                    )
                    putString(
                        ARGUMENTS.PLANT_SCHEDULE.key,
                        binding.inputScheduleText?.editText?.text.toString()
                    )
                }

                setFragmentResult("addRequestKey", result)
                parentFragmentManager.popBackStack()
            }
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

        binding.plantTypeSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
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
        binding.inputScheduleText?.editText?.setText(schedule)
    }
}
