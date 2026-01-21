from pydantic import BaseModel, Field
from typing import List

class CreditRiskInput(BaseModel):
    limit_bal: float = Field(ge=0)
    sex: int
    education: int
    marriage: int
    age: int

    pay_0: int
    pay_2: int
    pay_3: int
    pay_4: int
    pay_5: int
    pay_6: int

    bill_amt1: float
    bill_amt2: float
    bill_amt3: float

    pay_amt1: float
    pay_amt2: float
    pay_amt3: float

class CreditRiskResponse(BaseModel):
    prob_default: float
    risk_label: str
    model_version: str

# batch
class CreditRiskItem(BaseModel):
    probability: float
    risk: str

# Meta do batch
class BatchMeta(BaseModel):
    model_version: str
    total: int

# /predict/batch 
class CreditBatchResponse(BaseModel):
    meta: BatchMeta
    results: List[CreditRiskItem]
