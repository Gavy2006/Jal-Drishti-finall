package com.example.jaldrishtifinalll.model

data class RainfallResponse(
    val location: Location,
    val annual_rainfall_mm: Double,
    val roof_type: String,
    val roof_area_m2: Double,
    val runoff_coefficient_used: Double,
    val harvestable_litres: Double
)

data class Location(
    val lat: Double,
    val lon: Double
)