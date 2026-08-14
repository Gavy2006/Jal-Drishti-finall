package com.example.jaldrishtifinalll.model

data class RagReportResponse(
    val report: DetailedReport
)

data class DetailedReport(
    val executive_summary: String,

    // Site analysis
    val location_analysis: String,
    val roof_analysis: String,
    val rainfall_analysis: String,
    val harvesting_potential: String,
    val feasibility: String,

    // System recommendation
    val recommended_system: String,
    val tank_recommendation: String,
    val filtration_recommendation: String,
    val installation_guidance: String,

    // Financial
    val cost_estimation: String,
    val component_cost_breakdown: String,
    val maintenance_cost: String,

    // Government / policy
    val government_policies: String,
    val applicable_subsidies: String,
    val policy_year: String,

    // Benefits
    val environmental_benefits: String,
    val water_savings: String,

    // Final
    val recommendations: String,
    val sources: List<String>
)