package com.example.jaldrishtifinalll.model

data class RainfallRequest(
    val place: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val roof_type: String,
    val roof_area_m2: Double
)