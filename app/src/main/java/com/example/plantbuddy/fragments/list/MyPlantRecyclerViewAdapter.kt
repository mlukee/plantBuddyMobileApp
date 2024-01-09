package com.example.plantbuddy.fragments.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.plantbuddy.R
import com.example.plantbuddy.model.Plant

import com.example.plantbuddy.databinding.FragmentItemBinding

class MyPlantRecyclerViewAdapter(
    private val onItemClick: ((Plant) -> Unit),
    private var onItemLongClick: ((Plant) -> Unit)?
) : RecyclerView.Adapter<MyPlantRecyclerViewAdapter.MyViewHolder>() {


    private var plantList = emptyList<Plant>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FragmentItemBinding.bind(itemView)
        val plantName = binding.plantName
        val plantType = binding.plantType
        val plantSchedule = binding.plantSchedule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
        )
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val plant = plantList[position]
        holder.plantName.text = plant.name
        holder.plantType.text = plant.plantType
        holder.plantSchedule.text = plant.wateringSchedule

        holder.itemView.setOnClickListener {

            //Open update fragment and pass plant
            onItemClick.invoke(plant)

        }

        holder.itemView.setOnLongClickListener {
            // Handle long click
            onItemLongClick?.invoke(plant)
            true
        }
    }

    fun setData(plant: List<Plant>) {
        this.plantList = plant
        notifyDataSetChanged()
    }
}