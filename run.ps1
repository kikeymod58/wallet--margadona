# Script de ejecución para Windows (PowerShell)
# Ejecuta la aplicación Wallet

Write-Host ""

# Verificar si existe el directorio compilado
if (-not (Test-Path "target\classes")) {
    Write-Host "❌ El proyecto no está compilado." -ForegroundColor Red
    Write-Host "   Ejecuta primero: .\compile.ps1" -ForegroundColor Yellow
    Write-Host ""
    exit 1
}

# Ejecutar la aplicación
java -cp target\classes com.wallet.Main

Write-Host ""
Write-Host "════════════════════════════════════════" -ForegroundColor Cyan
