from fastapi import FastAPI, HTTPException

from app.models.report_models import (
    DetailedReport,
    ReportRequest,
)

from app.services.report_service import (
    generate_report,
)


app = FastAPI(
    title="Jal Drishti RAG Backend",
    version="1.0.0",
)


@app.get("/")
def root():
    return {
        "message": "Jal Drishti RAG Backend is running"
    }


@app.get("/health")
def health():
    return {
        "status": "ok"
    }


@app.post(
    "/api/report",
    response_model=DetailedReport,
)
def create_report(
    request: ReportRequest,
):
    try:

        report = generate_report(
            request
        )

        return report

    except Exception as e:

        raise HTTPException(
            status_code=500,
            detail=str(e),
        )