package com.example.lib

import java.util.UUID

class Store(
    var storeName: String,
    var storeLocation: String,
    var latitude: Double,
    var longitude: Double,
    var storeHours: String,
    val id: String = UUID.randomUUID().toString()
) {}
