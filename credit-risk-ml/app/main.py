from fastapi import FastAPI, UploadFile, File, HTTPException
from fastapi.responses import Response
from dataclasses import replace


from .settings import settings
from .model_loader import load_model
from .schemas import CreditRiskInput, CreditRiskResponse, CreditBatchResponse, BatchMeta
from .service import predict_one, predict_many
from .tools.csv_tools import template_csv_text, read_csv_bytes_to_dicts

app = FastAPI(title="Credit Risk ML Service", version="0.1.0")

try:
    MODEL = load_model(settings.artifact_path)

    # thresholds do projeto
    MODEL = replace(MODEL, thresholds=settings.thresholds)
    LOAD_ERROR = None
except Exception as e:
    MODEL = None
    LOAD_ERROR = str(e)

@app.get("/health")
def health():
    if MODEL is None:
        return {"status": "error", "error": LOAD_ERROR}
    return {"status": "ok", "model_version": MODEL.version, "thresholds": MODEL.thresholds}


@app.get("/tools/csv-template")
def csv_template():
    return Response(
        content=template_csv_text(),
        media_type="text/csv",
        headers={"Content-Disposition": 'attachment; filename="credit-risk-template.csv"'}
    )

@app.post("/predict", response_model=CreditRiskResponse)
def predict(input: CreditRiskInput):
    if MODEL is None:
        raise HTTPException(status_code=500, detail=f"Model not loaded: {LOAD_ERROR}")
    return predict_one(MODEL, input)

@app.post("/predict/batch", response_model=CreditBatchResponse)
async def predict_batch(file: UploadFile = File(...)):
    if MODEL is None:
        raise HTTPException(status_code=500, detail=f"Model not loaded: {LOAD_ERROR}")

    content = await file.read()
    rows = read_csv_bytes_to_dicts(content)

    try:
        items = [CreditRiskInput(**r) for r in rows]
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Invalid CSV rows: {e}")

    results = predict_many(MODEL, items)

    return CreditBatchResponse(
        meta=BatchMeta(
            model_version=MODEL.version,
            total=len(results)
        ),
        results=results
    )
