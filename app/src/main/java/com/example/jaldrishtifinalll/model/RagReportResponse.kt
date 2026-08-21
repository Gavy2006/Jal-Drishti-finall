package com.example.jaldrishtifinalll.model

data class RagReportResponse(
    val executive_summary: String,
    val location_analysis: String,
    val roof_analysis: String,
    val rainfall_analysis: String,
    val harvesting_potential: String,
    val feasibility: String,

    val recommended_system: String,
    val tank_recommendation: String,
    val filtration_recommendation: String,
    val installation_guidance: String,

    val cost_estimation: String,
    val component_cost_breakdown: String,
    val maintenance_cost: String,

    val government_policies: String,
    val applicable_subsidies: String,
    val policy_year: String,

    val environmental_benefits: String,
    val water_savings: String,

    val recommendations: String,
    val sources: List<String>
)