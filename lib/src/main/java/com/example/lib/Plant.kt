package com.example.lib

import java.util.UUID

class Plant(
    var name: String,
    var plantType: String,
    var wateringSchedule: String,
    var lastWatering: Long,
    val id: String = UUID.randomUUID().toString()
)