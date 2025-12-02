# Script para compilar y ejecutar los tests
# Ejecutar: .\test.ps1

Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Ejecutando Tests Unitarios" -ForegroundColor Cyan
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host ""

# Paso 1: Asegurar que el código fuente esté compilado
Write-Host "1. Verificando codigo fuente compilado..." -ForegroundColor Yellow

if (-not (Test-Path "target\classes")) {
    Write-Host "Compilando codigo fuente..." -ForegroundColor Yellow
    & .\compile.ps1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Error compilando codigo fuente" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "Codigo fuente ya compilado" -ForegroundColor Green
}

Write-Host ""

# Paso 2: Crear directorio para tests
Write-Host "2. Preparando directorio de tests..." -ForegroundColor Yellow

if (-not (Test-Path "target\test-classes")) {
    New-Item -ItemType Directory -Path "target\test-classes" -Force | Out-Null
}

Write-Host "Directorio listo" -ForegroundColor Green
Write-Host ""

# Paso 3: Compilar los tests
Write-Host "3. Compilando tests..." -ForegroundColor Yellow

$testFiles = Get-ChildItem -Path "src\test\java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }

if ($testFiles.Count -eq 0) {
    Write-Host "No se encontraron archivos de test" -ForegroundColor Red
    exit 1
}

$compiledCount = 0
$errorCount = 0

foreach ($testFile in $testFiles) {
    $result = javac -encoding UTF-8 -d target\test-classes -cp "lib\*;target\classes;target\test-classes" $testFile 2>&1
    if ($LASTEXITCODE -eq 0) {
        $compiledCount++
    } else {
        $errorCount++
        Write-Host "Error compilando: $testFile" -ForegroundColor Red
        Write-Host $result -ForegroundColor Red
    }
}

if ($errorCount -gt 0) {
    Write-Host "Tests compilados: $compiledCount, Errores: $errorCount" -ForegroundColor Red
    exit 1
}

Write-Host "Tests compilados exitosamente ($compiledCount archivos)" -ForegroundColor Green

Write-Host ""

# Paso 4: Ejecutar los tests
Write-Host "4. Ejecutando tests..." -ForegroundColor Yellow
Write-Host ""

# Construir el classpath con todos los JARs
$jars = Get-ChildItem -Path "lib" -Filter "*.jar" | ForEach-Object { $_.FullName }
$classpath = "target\classes;target\test-classes;" + ($jars -join ";")

java -jar lib\junit-platform-console-standalone-1.10.1.jar `
    --class-path $classpath `
    --scan-class-path `
    --disable-ansi-colors

Write-Host ""
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Tests completados" -ForegroundColor Green
Write-Host "===========================================================" -ForegroundColor Cyan
