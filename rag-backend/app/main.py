from fastapi import FastAPI

from app.models.report_models import ReportRequest


app = FastAPI(
    title="Jal Drishti RAG Backend",
    version="1.0.0"
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


@app.post("/api/report")
def create_report(request: ReportRequest):
    return {
        "status": "received",
        "data": request.model_dump()
    }