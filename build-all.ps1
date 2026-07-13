$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$maven = Get-ChildItem "$env:USERPROFILE\.m2\wrapper\dists" -Recurse -Filter mvn.cmd | Select-Object -First 1 -ExpandProperty FullName
if (-not $maven) { throw "No se encontro Maven. Ejecuta cualquier mvnw.cmd una vez desde IntelliJ." }

$modules = @(
    "common-models", "discovery-server", "config-server", "user-service",
    "product-service", "client-service", "cloud-gateway",
    "authentication-server-oauth2", "authentication-client-oauth2",
    "audit-service", "product-query-service", "order-saga-service"
)

foreach ($module in $modules) {
    Write-Host "Compilando $module..."
    $goal = if ($module -eq "common-models") { "install" } else { "package" }
    & $maven -f "$root\$module\pom.xml" $goal
    if ($LASTEXITCODE -ne 0) { throw "Fallo la compilacion de $module" }
}
Write-Host "Todos los modulos compilaron correctamente."
