package com.example.plantbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.plantbuddy.databinding.FragmentInputBinding


class InputFragment : Fragment(R.layout.fragment_input) {
    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(layoutInflater)

        binding.addPlant.setOnClickListener {
            val result = Bundle().apply {
                putString("inputKey", binding.inputNameText.editText?.text.toString())
            }

            // Set the result with a unique requestKey and the bundle
            setFragmentResult("requestKey", result)

            // Optionally close the fragment
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}