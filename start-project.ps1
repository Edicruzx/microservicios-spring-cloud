param(
    [switch]$FullDocker
)

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$compose = Join-Path $root "docker-files\docker-compose.yml"
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

& $docker info *> $null
if ($LASTEXITCODE -ne 0) {
    throw "El motor de Docker no esta disponible. Inicia Docker Desktop y vuelve a ejecutar este script."
}

$composeArguments = @("compose", "-f", $compose)
if ($FullDocker) {
    $composeArguments += @("--profile", "application", "--profile", "tools")
}
$composeArguments += @("up", "-d", "--build", "--remove-orphans")

& $docker @composeArguments
if ($LASTEXITCODE -ne 0) {
    throw "Docker Compose no pudo construir o iniciar el proyecto."
}

& $docker @("compose", "-f", $compose, "ps")
if ($LASTEXITCODE -ne 0) {
    throw "No se pudo consultar el estado de los contenedores."
}

if ($FullDocker) {
    Write-Host "Proyecto completo iniciado en Docker. Gateway: http://localhost:9080 - Eureka: http://localhost:8761"
} else {
    Write-Host "Infraestructura esencial iniciada: MongoDB:27018 Kafka:9092"
}
