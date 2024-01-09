package com.example.plantbuddy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "plant_table")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var plantType: String,
    var wateringSchedule: String,
    var lastWatering: Long,
) : Parcelable