# Microserviço DEM (Data Extraction Management)

## Build e Execução

### Build local
```sh
./mvnw clean package
```

### Docker
```sh
docker build -t dem .
docker run --rm -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/demdb -e SPRING_DATASOURCE_USERNAME=demuser -e SPRING_DATASOURCE_PASSWORD=dempass -e MDM_API_URL=http://localhost:8081 -p 8080:8080 dem
```

## Endpoints principais
- `POST /api/etl/start` — Inicia extração e transformação dos dados
- `POST /api/etl/load/{transactionId}` — Carrega dados transformados no MDM
- `GET /api/etl/transactions` — Lista transações ETL e status

## Swagger UI
Acesse a documentação interativa em: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html) 