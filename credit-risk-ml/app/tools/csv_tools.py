from io import StringIO
import pandas as pd

COLUMNS = [
    "limit_bal","sex","education","marriage","age",
    "pay_0","pay_2","pay_3","pay_4","pay_5","pay_6",
    "bill_amt1","bill_amt2","bill_amt3",
    "pay_amt1","pay_amt2","pay_amt3"
]

def template_csv_text() -> str:
    return ",".join(COLUMNS) + "\n"

def read_csv_bytes_to_dicts(content: bytes) -> list[dict]:
    df = pd.read_csv(StringIO(content.decode("utf-8")))
    df = df[[c for c in COLUMNS if c in df.columns]]
    return df.to_dict(orient="records")
