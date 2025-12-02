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

# Definir dependencias
$dependencies = @(
    @{
        Name = "JUnit Platform Console Standalone"
        Url = "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar"
        File = "lib\junit-platform-console-standalone-1.10.1.jar"
    },
    @{
        Name = "Mockito Core"
        Url = "https://repo1.maven.org/maven2/org/mockito/mockito-core/5.8.0/mockito-core-5.8.0.jar"
        File = "lib\mockito-core-5.8.0.jar"
    },
    @{
        Name = "Byte Buddy (Mockito dependency)"
        Url = "https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.14.11/byte-buddy-1.14.11.jar"
        File = "lib\byte-buddy-1.14.11.jar"
    },
    @{
        Name = "Byte Buddy Agent (Mockito dependency)"
        Url = "https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.14.11/byte-buddy-agent-1.14.11.jar"
        File = "lib\byte-buddy-agent-1.14.11.jar"
    },
    @{
        Name = "Objenesis (Mockito dependency)"
        Url = "https://repo1.maven.org/maven2/org/objenesis/objenesis/3.3/objenesis-3.3.jar"
        File = "lib\objenesis-3.3.jar"
    }
)

# Descargar cada dependencia
foreach ($dep in $dependencies) {
    Write-Host "Descargando $($dep.Name)..." -ForegroundColor Yellow
    
    if (Test-Path $dep.File) {
        Write-Host "  Ya existe, omitiendo..." -ForegroundColor Gray
    }
    else {
        try {
            Invoke-WebRequest -Uri $dep.Url -OutFile $dep.File -UseBasicParsing
            Write-Host "  $($dep.Name) descargado exitosamente" -ForegroundColor Green
        }
        catch {
            Write-Host "  Error descargando $($dep.Name): $_" -ForegroundColor Red
        }
    }
}

Write-Host ""
Write-Host "===========================================================" -ForegroundColor Cyan
Write-Host "  Descarga completada" -ForegroundColor Green
Write-Host "===========================================================" -ForegroundColor Cyan
