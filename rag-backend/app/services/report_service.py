import json
import re

from app.models.report_models import (
    DetailedReport,
    ReportRequest,
)

from app.rag.retriever import retrieve_context
from app.rag.llm import generate_answer


def _clean_json_response(text: str) -> str:
    """
    Removes markdown code fences if the LLM
    wraps the JSON response inside ```json ... ```.
    """

    text = text.strip()

    text = re.sub(
        r"^```json\s*",
        "",
        text,
        flags=re.IGNORECASE,
    )

    text = re.sub(
        r"\s*```$",
        "",
        text,
    )

    return text.strip()


def generate_report(
    request: ReportRequest,
) -> DetailedReport:

    query = f"""
Generate a detailed rainwater harvesting feasibility report.

Location:
Latitude: {request.latitude}
Longitude: {request.longitude}

Roof:
Area: {request.roof_area_m2} m2
Type: {request.roof_type}

Known calculated data:
Annual rainfall: {request.annual_rainfall_mm}
Harvestable litres: {request.harvestable_litres}
Runoff coefficient: {request.runoff_coefficient}

The report must cover:
- site and location analysis
- roof analysis
- rainfall analysis
- harvesting potential
- feasibility
- recommended harvesting system
- tank recommendation
- filtration recommendation
- installation guidance
- cost estimation
- component-wise cost breakdown
- maintenance cost
- government policies
- applicable subsidies
- policy year
- environmental benefits
- water savings
- final recommendations

Use the provided RAG context as the primary source.

IMPORTANT:
1. Do not invent government policies, subsidies, costs, or policy years.
2. If the provided sources do not contain reliable information for a field,
   clearly state that the information is not available in the provided sources.
3. Keep costs and policies tied to the available source/year.
4. Return ONLY valid JSON.
5. Do not wrap the JSON in markdown code fences.

Return exactly these fields:

{{
    "executive_summary": "",
    "location_analysis": "",
    "roof_analysis": "",
    "rainfall_analysis": "",
    "harvesting_potential": "",
    "feasibility": "",
    "recommended_system": "",
    "tank_recommendation": "",
    "filtration_recommendation": "",
    "installation_guidance": "",
    "cost_estimation": "",
    "component_cost_breakdown": "",
    "maintenance_cost": "",
    "government_policies": "",
    "applicable_subsidies": "",
    "policy_year": "",
    "environmental_benefits": "",
    "water_savings": "",
    "recommendations": "",
    "sources": []
}}
"""

    context_items = retrieve_context(
        query=query,
        top_k=8,
    )

    context_text = "\n\n".join(
        f"""
SOURCE: {item.get("source")}
PAGE: {item.get("page")}

{item.get("text")}
"""
        for item in context_items
    )

    prompt = f"""
You are the report-generation engine for Jal Drishti.

USER INPUT:
{query}

RETRIEVED RAG CONTEXT:
{context_text if context_text else "No relevant documents are currently available."}

Generate the final report using the instructions above.

Return ONLY valid JSON.
"""

    response_text = generate_answer(prompt)

    cleaned_response = _clean_json_response(
        response_text
    )

    try:
        report_data = json.loads(
            cleaned_response
        )

    except json.JSONDecodeError as e:
        raise ValueError(
            "LLM returned invalid JSON"
        ) from e

    return DetailedReport.model_validate(
        report_data
    )