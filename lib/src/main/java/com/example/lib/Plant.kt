package com.example.lib

import java.util.UUID

class Plant(
    val name: String,
    val id: UUID = UUID.randomUUID()
)