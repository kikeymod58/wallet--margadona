# Build and Run Script
Write-Host "Compilando proyecto..." -ForegroundColor Cyan

# Limpiar
if (Test-Path "target") { Remove-Item -Recurse -Force "target" }
New-Item -ItemType Directory -Force -Path "target\classes" | Out-Null

# Compilar
$files = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse
javac -d target\classes -encoding UTF-8 $files.FullName

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilacion exitosa!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ejecutando aplicacion..." -ForegroundColor Cyan
    Write-Host ""
    java -cp target\classes com.wallet.Main
    Write-Host ""
}
