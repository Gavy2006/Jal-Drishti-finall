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