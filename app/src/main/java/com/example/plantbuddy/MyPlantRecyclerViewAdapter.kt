package com.example.plantbuddy

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lib.Plant

import com.example.plantbuddy.databinding.FragmentItemBinding

class MyPlantRecyclerViewAdapter(
    private val app: MyApplication,
) : RecyclerView.Adapter<MyPlantRecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((Plant) -> Unit)? = null
    var onItemLongClick: ((Int) -> Unit)? = null

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

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(app.plants.plants[position])
                }
            }

            itemView.setOnLongClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClick?.invoke(position)
                }
                true
            }
        }

    }

//    fun addPlant(plant: Plant) {
//        app.plants.addPlant(plant)
//        notifyItemInserted(app.plants.plants.size - 1)
//    }
//
//    fun removePlant(position:Int) {
//        app.plants.removePlantByPosition(position)
//        notifyItemRemoved(position)
//    }

}