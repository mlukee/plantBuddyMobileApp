package com.example.plantbuddy

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A fragment representing a list of Items.
 */
class PlantFragment : Fragment(R.layout.fragment_item_list) {

    private lateinit var adapter: MyPlantRecyclerViewAdapter
    private lateinit var app: MyApplication

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MyApplication

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        adapter = MyPlantRecyclerViewAdapter(app).apply {
            onItemClick = { plant ->
                val inputFragment = InputFragment().apply {
                    arguments = Bundle().apply {
                        putString("plantName", plant.name)
                        putString("plantID", plant.id)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment, inputFragment)
                    .addToBackStack(null)
                    .commit()
            }

            onItemLongClick = { position ->
                AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Do you really want to delete this plant?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // Delete the plant from the list
                        app.removePlantByPosition(position)
                        notifyItemRemoved(position)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }

        // Set the adapter to the RecyclerView
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = this@PlantFragment.adapter
            }
        }
        return view
    }


    fun updatePlants(position: Int) {
        adapter.notifyItemChanged(position)
    }

}