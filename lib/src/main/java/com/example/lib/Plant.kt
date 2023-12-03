package com.example.lib

import java.util.UUID

class Plant(
    var name: String,
    val id: UUID = UUID.randomUUID()
)