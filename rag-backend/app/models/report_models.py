from typing import Optional

from pydantic import BaseModel


class ReportRequest(BaseModel):
    latitude: float
    longitude: float
    roof_area_m2: float
    roof_type: str

    annual_rainfall_mm: Optional[float] = None
    harvestable_litres: Optional[float] = None
    runoff_coefficient: Optional[float] = None


class DetailedReport(BaseModel):
    executive_summary: str

    # Site analysis
    location_analysis: str
    roof_analysis: str
    rainfall_analysis: str
    harvesting_potential: str
    feasibility: str

    # System recommendation
    recommended_system: str
    tank_recommendation: str
    filtration_recommendation: str
    installation_guidance: str

    # Financial
    cost_estimation: str
    component_cost_breakdown: str
    maintenance_cost: str

    # Government / policy
    government_policies: str
    applicable_subsidies: str
    policy_year: str

    # Benefits
    environmental_benefits: str
    water_savings: str

    # Final
    recommendations: str
    sources: list[str]