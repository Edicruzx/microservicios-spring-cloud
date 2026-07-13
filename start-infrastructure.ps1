$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
docker compose -f "$root\docker-files\docker-compose.yml" up -d mongo-service mongo-express postgres-mitocode zookeeper broker zipkin prometheus grafana
if ($LASTEXITCODE -ne 0) { throw "Docker Compose no pudo iniciar la infraestructura." }
docker compose -f "$root\docker-files\docker-compose.yml" ps
if ($LASTEXITCODE -ne 0) { throw "No se pudo consultar el estado de los contenedores." }

Write-Host "Infraestructura iniciada. MongoDB:27018 Kafka:9092 Zipkin:9411 Prometheus:9090 Grafana:3000"
