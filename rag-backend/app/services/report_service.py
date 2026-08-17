import json
import re

from app.models.report_models import (
    DetailedReport,
    ReportRequest,
)

from app.rag.retriever import retrieve_context
from app.rag.llm import generate_answer


def _clean_json_response(text: str) -> str:
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


def _merge_contexts(
    contexts: list[dict],
) -> list[dict]:
    """
    Remove duplicate source-page combinations.
    """

    seen = set()
    unique_contexts = []

    for item in contexts:
        key = (
            item.get("source"),
            item.get("page"),
        )

        if key in seen:
            continue

        seen.add(key)
        unique_contexts.append(item)

    return unique_contexts


def generate_report(
    request: ReportRequest,
) -> DetailedReport:

    base_data = f"""
Location:
Latitude: {request.latitude}
Longitude: {request.longitude}

Roof:
Area: {request.roof_area_m2} m2
Type: {request.roof_type}

Known calculated values:
Annual rainfall: {request.annual_rainfall_mm}
Harvestable litres: {request.harvestable_litres}
Runoff coefficient: {request.runoff_coefficient}
"""

    # Separate retrieval queries improve coverage
    # across technical, policy, cost and government information.

    technical_query = f"""
Rainwater harvesting technical design and feasibility.

{base_data}

Find relevant information about:
- rooftop catchment
- runoff coefficient
- rainfall analysis
- storage sizing
- filtration
- first flush
- recharge structures
- installation
- maintenance
- technical recommendations
"""

    policy_query = f"""
Government rules, building regulations and policy requirements
for rooftop rainwater harvesting.

Location:
Latitude: {request.latitude}
Longitude: {request.longitude}

Find relevant information about:
- Haryana building rules
- rainwater harvesting requirements
- mandatory provisions
- inspections
- compliance
- building bye-laws
- policy year
"""

    cost_query = f"""
Cost, construction rates and maintenance information related to
rainwater harvesting systems.

Find relevant information about:
- storage tanks
- pipes
- filters
- recharge structures
- installation
- labour
- maintenance
- government schedule of rates
- component costs
"""

    subsidy_query = f"""
Government schemes, subsidies, incentives and financial assistance
for rainwater harvesting or groundwater recharge.

Location:
Haryana, India

Find information about:
- scheme name
- eligibility
- subsidy amount
- incentive
- applicable year
- implementing authority
"""

    technical_context = retrieve_context(
        technical_query,
        top_k=5,
    )

    policy_context = retrieve_context(
        policy_query,
        top_k=5,
    )

    cost_context = retrieve_context(
        cost_query,
        top_k=5,
    )

    subsidy_context = retrieve_context(
        subsidy_query,
        top_k=5,
    )

    context_items = _merge_contexts(
        technical_context
        + policy_context
        + cost_context
        + subsidy_context
    )

    context_text = "\n\n".join(
        f"""
SOURCE: {item.get("source")}
PAGE: {item.get("page")}
CITATION: {item.get("citation")}

{item.get("text")}
"""
        for item in context_items
    )

    source_citations = [
        item["citation"]
        for item in context_items
        if item.get("citation")
    ]

    report_instructions = f"""
Generate a detailed rainwater harvesting feasibility report
for Jal Drishti.

USER DATA:
{base_data}

The report must include:

- executive summary
- location analysis
- roof analysis
- rainfall analysis
- harvesting potential
- feasibility
- recommended system
- tank recommendation
- filtration recommendation
- installation guidance
- cost estimation
- component cost breakdown
- maintenance cost
- government policies
- applicable subsidies
- policy year
- environmental benefits
- water savings
- recommendations
- sources

IMPORTANT RULES:

1. Use the retrieved documents as the primary knowledge source.
2. Do NOT invent costs, subsidies, government schemes,
   policy years or eligibility requirements.
3. If the retrieved sources do not provide reliable information,
   write:
   "Information not available in the provided sources."
4. User-provided calculated values must not be altered.
5. Do not invent a source or page number.
6. For sources, use only the provided citation list.
7. Return ONLY valid JSON.
8. Do not use markdown code fences.

Return exactly this JSON structure:

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

RETRIEVED SOURCES:

{json.dumps(source_citations, indent=2)}

RETRIEVED CONTEXT:

{context_text if context_text else "No relevant documents are currently available."}
"""

    response_text = generate_answer(
        report_instructions
    )

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

    # Never allow the LLM to invent citations.
    report_data["sources"] = source_citations

    return DetailedReport.model_validate(
        report_data
    )