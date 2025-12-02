# Script para descargar las dependencias de JUnit y otras librerias de prueba
# Ejecutar: .\download-dependencies.ps1

Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Descargando dependencias para tests..." -ForegroundColor Cyan
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host ""

# Crear directorio lib si no existe
if (-not (Test-Path "lib")) {
    New-Item -ItemType Directory -Path "lib" | Out-Null
    Write-Host "Directorio lib creado" -ForegroundColor Green
}

# URL de la dependencia
$junitUrl = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar"
$junitFile = "lib\junit-platform-console-standalone-1.10.1.jar"

# Descargar JUnit
Write-Host "Descargando JUnit Platform Console Standalone..." -ForegroundColor Yellow

try {
    Invoke-WebRequest -Uri $junitUrl -OutFile $junitFile -UseBasicParsing
    Write-Host "JUnit descargado exitosamente" -ForegroundColor Green
}
catch {
    Write-Host "Error descargando JUnit: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Descarga completada" -ForegroundColor Green
Write-Host "===========================================================" -ForegroundColor Cyan
