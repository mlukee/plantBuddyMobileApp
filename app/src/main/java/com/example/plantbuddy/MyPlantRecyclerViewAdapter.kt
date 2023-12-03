package com.example.plantbuddy

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lib.Plant

import com.example.plantbuddy.placeholder.PlaceholderContent.PlaceholderItem
import com.example.plantbuddy.databinding.FragmentItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPlantRecyclerViewAdapter(
    private val app: MyApplication,
) : RecyclerView.Adapter<MyPlantRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = app.plants.plants[position]
        holder.plantName.text = plant.name
    }

    override fun getItemCount(): Int = app.plants.plants.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val plantName = binding.plantName
    }

    fun addPlant(plant: Plant) {
        app.plants.addPlant(plant)
        notifyItemInserted(app.plants.plants.size - 1)
    }

    fun removePlant(position:Int) {
        app.plants.removePlantByPosition(position)
        notifyItemRemoved(position)
    }

}