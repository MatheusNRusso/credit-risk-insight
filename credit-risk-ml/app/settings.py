from pydantic import BaseModel
from pathlib import Path

class Settings(BaseModel):
    artifact_path: Path = Path("artifacts/credit-risk-uci-v1.joblib")

    thresholds: dict = {"LOW_MAX": 0.30, "MEDIUM_MAX": 0.60}


settings = Settings()
