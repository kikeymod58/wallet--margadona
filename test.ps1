# Script para compilar y ejecutar los tests
# Ejecutar: .\test.ps1

Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Ejecutando Tests Unitarios" -ForegroundColor Cyan
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host ""

# Paso 1: Compilar el código fuente
Write-Host "1. Compilando codigo fuente..." -ForegroundColor Yellow

$sourceFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
$sourceList = $sourceFiles -join " "

$compileCmd = "javac -encoding UTF-8 -d out -cp `"lib\*`" $sourceList"
$result = Invoke-Expression $compileCmd 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "Codigo fuente compilado exitosamente" -ForegroundColor Green
} else {
    Write-Host "Error compilando codigo fuente:" -ForegroundColor Red
    Write-Host $result -ForegroundColor Red
    exit 1
}

Write-Host ""

# Paso 2: Compilar los tests
Write-Host "2. Compilando tests..." -ForegroundColor Yellow

$testFiles = Get-ChildItem -Path "src\test\java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
$testList = $testFiles -join " "

$compileTestCmd = "javac -encoding UTF-8 -d out -cp `"lib\*;out`" $testList"
$result = Invoke-Expression $compileTestCmd 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host "Tests compilados exitosamente" -ForegroundColor Green
} else {
    Write-Host "Error compilando tests:" -ForegroundColor Red
    Write-Host $result -ForegroundColor Red
    exit 1
}

Write-Host ""

# Paso 3: Ejecutar los tests
Write-Host "3. Ejecutando tests..." -ForegroundColor Yellow
Write-Host ""

java -jar lib\junit-platform-console-standalone-1.10.1.jar `
    --class-path out `
    --scan-class-path `
    --disable-ansi-colors

Write-Host ""
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Tests completados" -ForegroundColor Green
Write-Host "===========================================================" -ForegroundColor Cyan
