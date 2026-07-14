$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$dockerCommand = Get-Command docker -ErrorAction SilentlyContinue
$docker = if ($dockerCommand) {
    $dockerCommand.Source
} else {
    $localDocker = Join-Path $env:LOCALAPPDATA "Programs\DockerDesktop\resources\bin\docker.exe"
    if (Test-Path -LiteralPath $localDocker) { $localDocker }
}

if (-not $docker) {
    throw "Docker no esta instalado o no esta disponible. Instala/inicia Docker Desktop y vuelve a ejecutar este script."
}

& $docker compose -f "$root\docker-files\docker-compose.yml" up -d --remove-orphans mongo-service zookeeper broker
if ($LASTEXITCODE -ne 0) { throw "Docker Compose no pudo iniciar la infraestructura." }
& $docker compose -f "$root\docker-files\docker-compose.yml" ps
if ($LASTEXITCODE -ne 0) { throw "No se pudo consultar el estado de los contenedores." }

Write-Host "Infraestructura esencial iniciada. MongoDB:27018 Kafka:9092"
