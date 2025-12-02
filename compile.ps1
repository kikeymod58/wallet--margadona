# Script de compilación para Windows (PowerShell)
# Compila el proyecto Wallet sin necesidad de Maven

Write-Host "Compilando Proyecto Wallet..." -ForegroundColor Cyan
Write-Host ""

# Limpiar directorio target
if (Test-Path "target") {
    Write-Host "Limpiando directorio target..." -ForegroundColor Yellow
    Remove-Item -Recurse -Force "target"
}

# Crear directorios
Write-Host "Creando estructura de directorios..." -ForegroundColor Yellow
New-Item -ItemType Directory -Force -Path "target\classes" | Out-Null
New-Item -ItemType Directory -Force -Path "target\test-classes" | Out-Null

# Compilar código fuente
Write-Host "Compilando codigo fuente..." -ForegroundColor Yellow
$sourceFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

if ($sourceFiles.Count -eq 0) {
    Write-Host "No se encontraron archivos Java para compilar" -ForegroundColor Red
    exit 1
}

javac -d target\classes -encoding UTF-8 $sourceFiles

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilacion exitosa!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Archivos compilados en: target\classes" -ForegroundColor Green
    Write-Host ""
    Write-Host "Para ejecutar la aplicacion, usa:" -ForegroundColor Cyan
    Write-Host "   .\run.ps1" -ForegroundColor White
} else {
    Write-Host "Error en la compilacion" -ForegroundColor Red
    exit 1
}
