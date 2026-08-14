package com.example.jaldrishtifinalll.model

data class RagReportRequest(
    val latitude: Double,
    val longitude: Double,
    val roof_area_m2: Double,
    val roof_type: String,
    val annual_rainfall_mm: Double? = null,
    val harvestable_litres: Double? = null,
    val runoff_coefficient: Double? = null
)