from dataclasses import dataclass
from pathlib import Path
import joblib

@dataclass(frozen=True)
class LoadedModel:
    pipeline: object
    features: list[str]
    thresholds: dict
    version: str

def load_model(artifact_path: Path) -> LoadedModel:
    artifact = joblib.load(artifact_path)

    return LoadedModel(
        pipeline=artifact["pipeline"],
        features=artifact["feature_contract"],
        thresholds={},
        version=artifact.get("version", "credit-risk-unknown"),
    )
