package com.example.plantbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding = FragmentInputBinding.inflate(layoutInflater)

        arguments?.getString(ARGUMENTS.PLANT_NAME.key)?.let {
            binding.inputNameText.editText?.setText(it)
        }
        arguments?.getString(ARGUMENTS.PLANT_TYPE.key)?.let {
            binding.inputTypeText?.editText?.setText(it)
        }
        arguments?.getString(ARGUMENTS.PLANT_SCHEDULE.key)?.let {
            binding.inputScheduleText?.editText?.setText(it)
        }

        plantID = arguments?.getString(ARGUMENTS.PLANT_ID.key)

        val isEditing = arguments?.containsKey(ARGUMENTS.PLANT_NAME.key) == true
        val requestKey = if (isEditing) "editRequestKey" else "addRequestKey"
        if (isEditing) {
            binding.fragmentInputHeading.text = "UPDATE PLANT"
        }

        binding.addPlant.setOnClickListener {
            val result = Bundle().apply {
                if (isEditing) {
                    putString(ARGUMENTS.PLANT_ID.key, plantID)
                }
                putString(ARGUMENTS.PLANT_NAME.key, binding.inputNameText.editText?.text.toString())
                putString(ARGUMENTS.PLANT_TYPE.key, binding.inputTypeText?.editText?.text.toString())
                putString(
                    ARGUMENTS.PLANT_SCHEDULE.key,
                    binding.inputScheduleText?.editText?.text.toString()
                )
            }

            // Set the result with a unique requestKey and the bundle
            setFragmentResult(requestKey, result)

            // Optionally close the fragment
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}