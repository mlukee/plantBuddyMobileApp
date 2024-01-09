package com.example.plantbuddy.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.plantbuddy.R
import com.example.plantbuddy.fragments.update.UpdateFragment
import com.example.plantbuddy.model.Plant
import com.example.plantbuddy.viewmodel.PlantViewModel

/**
 * A fragment representing a list of Items.
 */
class PlantFragment : Fragment(R.layout.fragment_item_list) {


    private lateinit var mPlantViewModel: PlantViewModel

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        //RecyclerView
        val adapter = MyPlantRecyclerViewAdapter(
            onItemClick = { plant ->
                openUpdateFragment(plant)
            },
            onItemLongClick = { plant ->
                handleLongPress(plant)
            }
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //PlantViewModel
        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]
        mPlantViewModel.readAllData.observe(viewLifecycleOwner) { plant ->
            adapter.setData(plant)
        }

        return view
    }

    private fun handleLongPress(plant: Plant) {
        //Create an alert dialog
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mPlantViewModel.deletePlant(plant)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Are you sure you want to delete ${plant.name}?")
        builder.setMessage("This action cannot be undone.")
        builder.create().show()
    }

    private fun openUpdateFragment(plant: Plant) {
        val updateFragment = UpdateFragment().apply {
            arguments = Bundle().apply {
                putParcelable("plant_key", plant) // Make sure Plant implements Parcelable
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment, updateFragment)
            .addToBackStack(null) // Optional, if you want to add the transaction to the back stack
            .commit()
    }

}