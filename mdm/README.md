# Microserviço MDM (Master Data Management)

## Build e Execução

### Build local
```sh
./mvnw clean package
```

### Docker
```sh
docker build -t mdm .
docker run --rm -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/mdmdb -e SPRING_DATASOURCE_USERNAME=mdmuser -e SPRING_DATASOURCE_PASSWORD=mdmpass -p 8080:8080 mdm
```

## Endpoints principais
- `GET /api/countries` — Lista países (filtros: name, code)
- `GET /api/countries/{id}` — Busca país por ID
- `POST /api/countries` — Cria país
- `PUT /api/countries/{id}` — Atualiza país
- `DELETE /api/countries/{id}` — Remove país

## Swagger UI
Acesse a documentação interativa em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 