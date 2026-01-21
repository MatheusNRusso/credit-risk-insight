# Credit Risk Insight — Spring Boot API + ML (FastAPI)

Projeto que demonstra uma **integração completa entre uma API Java (Spring Boot)** e um **serviço de Machine Learning (Python/FastAPI)** para predição de risco de inadimplência.

✅ API REST funcional
✅ Serviço ML funcional
✅ Mapeamento camelCase (Java) → snake_case (Python) validado
✅ Persistência de predições em PostgreSQL (com opção H2)
✅ Swagger/OpenAPI + Actuator

---

## 🧠 Visão Geral

O projeto é composto por **dois serviços independentes**, que se comunicam via HTTP:

### 1️⃣ `creditapi` — Java / Spring Boot
Responsável por:
- Receber requisições de predição (`/api/predict`)
- Validar DTOs (Bean Validation)
- Converter dados de **camelCase** para **snake_case**
- Consumir o serviço de Machine Learning
- Persistir os resultados das predições (JPA/Hibernate)
- Expor documentação (Swagger) e healthcheck (Actuator)

### 2️⃣ `ml-service` — Python / FastAPI
Responsável por:
- Receber payload com features numéricas
- Executar inferência do modelo treinado
- Retornar:
  - probabilidade de inadimplência
  - rótulo de risco
  - versão do modelo

---

## 🧱 Stack Tecnológica

### Backend (API)
- Java 17
- Spring Boot
  - Web
  - Validation
  - Data JPA
  - WebFlux (WebClient)
  - Actuator
- PostgreSQL / H2
- Swagger / OpenAPI (springdoc)
- MapStruct

### Machine Learning
- Python 3.x
- FastAPI
- Pydantic
- Modelo serializado (artifact)

---

## 📌 Endpoints Principais

### API Java — `creditapi` (porta **8081**)
- `POST /api/predict` → realiza predição e persiste resultado
- `GET /actuator/health` → healthcheck da aplicação
- `GET /swagger-ui/index.html` → documentação interativa

### ML Service — `ml-service` (porta **8000**)
- `POST /predict` → retorna probabilidade e rótulo de risco

---

## ▶️ Como Rodar Localmente

### 1️⃣ Subir o PostgreSQL (opcional, recomendado)

```bash
docker run --name credit-risk-db \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=creditrisk \
  -p 5432:5432 -d postgres:15
```

> 💡 Caso prefira, é possível utilizar **H2 em memória** ajustando o `application.properties`.

---

### 2️⃣ Rodar o ML Service (Python)

```bash
cd ml-service
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
uvicorn app:app --host 0.0.0.0 --port 8000
```

---

### 3️⃣ Rodar a API Java (Spring Boot)

```bash
cd creditapi
./mvnw spring-boot:run
```

A API ficará disponível em:

* [http://localhost:8081](http://localhost:8081)

Swagger:

* [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

## ✅ Teste Rápido (cURL)

### Exemplo — Baixo risco

```bash
curl -s -X POST http://localhost:8081/api/predict \
  -H "Content-Type: application/json" \
  -d '{
    "limitBal": 350000,
    "sex": 1,
    "education": 1,
    "marriage": 2,
    "age": 42,
    "pay0": -1,
    "pay2": 0,
    "pay3": 0,
    "pay4": 0,
    "pay5": 0,
    "pay6": 0,
    "billAmt1": 12000,
    "billAmt2": 11000,
    "billAmt3": 9000,
    "payAmt1": 5000,
    "payAmt2": 4000,
    "payAmt3": 3000
  }'
```

### Exemplo — Alto risco

```bash
curl -s -X POST http://localhost:8081/api/predict \
  -H "Content-Type: application/json" \
  -d '{
    "limitBal": 80000,
    "sex": 2,
    "education": 2,
    "marriage": 1,
    "age": 35,
    "pay0": 2,
    "pay2": 2,
    "pay3": 2,
    "pay4": 2,
    "pay5": 2,
    "pay6": 2,
    "billAmt1": 70000,
    "billAmt2": 68000,
    "billAmt3": 65000,
    "payAmt1": 500,
    "payAmt2": 300,
    "payAmt3": 0
  }'
```

### Resposta esperada

```json
{
  "prob_default": 0.7167,
  "risk_label": "HIGH",
  "model_version": "credit-risk-uci-v1"
}
```

---

## 🗃️ Persistência

A cada requisição realizada em `/api/predict`, a API armazena:

* Probabilidade de inadimplência
* Rótulo de risco
* Versão do modelo
* Data e hora da predição

---

## 🔐 Observações Técnicas Importantes

* A API expõe contratos em **camelCase** (padrão Java).
* O payload enviado ao serviço de ML é convertido para **snake_case**.
* A integração entre Java ↔ Python foi validada com testes reais via `curl`.

