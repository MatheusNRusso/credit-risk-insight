import pandas as pd
from .model_loader import LoadedModel
from .schemas import CreditRiskInput, CreditRiskResponse, CreditRiskItem

def risk_label(p: float, thresholds: dict) -> str:
    low_max = thresholds["LOW_MAX"]
    med_max = thresholds["MEDIUM_MAX"]
    if p >= med_max:
        return "HIGH"
    if p >= low_max:
        return "MEDIUM"
    return "LOW"

def predict_one(model: LoadedModel, dto: CreditRiskInput) -> CreditRiskResponse:
    X = pd.DataFrame([dto.model_dump()], columns=model.features)
    proba = round(float(model.pipeline.predict_proba(X)[:, 1][0]), 4)

    return CreditRiskResponse(
        prob_default=proba,
        risk_label=risk_label(proba, model.thresholds),
        model_version=model.version
    )

def predict_many(model: LoadedModel, items: list[CreditRiskInput]) -> list[CreditRiskItem]:
    X = pd.DataFrame([it.model_dump() for it in items], columns=model.features)
    probas = model.pipeline.predict_proba(X)[:, 1].astype(float).tolist()

    results: list[CreditRiskItem] = []
    for p in probas:
        p = round(float(p), 4)
        results.append(
            CreditRiskItem(
                probability=p,
                risk=risk_label(p, model.thresholds)
            )
        )
    return results
