package com.example.lib

import java.util.UUID

class Plant(
    var name: String,
    var plantType: String,
    var wateringSchedule: String,
    val id: String = UUID.randomUUID().toString()
)