#!/bin/bash

set -e

# URLs
DEM_URL="http://localhost:8082/api/etl"
MDM_URL="http://localhost:8081/api/countries"

# 1. Iniciar extração e transformação
echo "\n== Iniciando extração e transformação no DEM =="
TX_JSON=$(curl -s -X POST "$DEM_URL/start" -H 'Content-Type: application/json')
echo "$TX_JSON" | jq
TX_ID=$(echo "$TX_JSON" | jq -r .id)

# 2. Listar transações ETL
echo "\n== Listando transações ETL no DEM =="
curl -s "$DEM_URL/transactions" | jq

# 3. Carregar dados transformados no MDM
echo "\n== Carregando dados transformados no MDM =="
curl -s -X POST "$DEM_URL/load/$TX_ID" -H 'Content-Type: application/json' -w '\nStatus: %{http_code}\n'

# 4. Listar países no MDM
sleep 2
echo "\n== Listando países no MDM =="
curl -s "$MDM_URL" | jq

# 5. Listar transações ETL novamente
echo "\n== Transações ETL após carga =="
curl -s "$DEM_URL/transactions" | jq 