# 💳 Wallet - Sistema de Gestión de Billetera Digital

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Tests](https://img.shields.io/badge/Tests-85%2F85-brightgreen.svg)](/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-blue.svg)](/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](/)

Sistema de billetera digital implementado en **Java 21** siguiendo los principios de **Clean Architecture**. Incluye gestión de usuarios, cuentas, depósitos, retiros y transferencias con validaciones robustas y almacenamiento en memoria.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Testing](#-testing)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Documentación](#-documentación)

---

## ✨ Características

### 👤 Gestión de Usuarios
- ✅ Registro de usuarios con validación de email y documento
- ✅ Búsqueda por ID, email o documento de identidad
- ✅ Activación/desactivación de usuarios
- ✅ Actualización de datos personales

### 💰 Gestión de Cuentas
- ✅ Creación automática de cuentas con número único
- ✅ Consulta de saldo en tiempo real
- ✅ Soporte para múltiples monedas (PEN, USD, EUR)
- ✅ Estado de cuenta (activa/inactiva)

### 💸 Operaciones Financieras
- ✅ **Depósitos**: Incremento de saldo con validaciones
- ✅ **Retiros**: Extracción de fondos con verificación de saldo
- ✅ **Transferencias**: Entre cuentas con registro de transacciones
- ✅ Historial completo de transacciones por cuenta

### 🔒 Validaciones y Seguridad
- ✅ Validación de saldo suficiente en retiros
- ✅ Prevención de emails duplicados
- ✅ Prevención de documentos duplicados
- ✅ Validación de montos positivos
- ✅ Verificación de cuentas activas
- ✅ Thread-safety en repositorios (ConcurrentHashMap)

---

## 🏗️ Arquitectura

El proyecto implementa **Clean Architecture** con 4 capas bien definidas:

```
┌─────────────────────────────────────────────────────┐
│               PRESENTATION LAYER                    │
│  (Console UI, Controllers, Menus, Utils)           │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│              APPLICATION LAYER                      │
│  (Use Cases, DTOs, Mappers, Requests/Responses)    │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│              INFRASTRUCTURE LAYER                   │
│  (Repositories, Services, Factories, Logger)        │
└─────────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────────┐
│                 DOMAIN LAYER                        │
│  (Entities, Value Objects, Exceptions, Interfaces) │
└─────────────────────────────────────────────────────┘
```

### Patrones de Diseño Implementados

- **Repository Pattern**: Abstracción del acceso a datos
- **Factory Pattern**: Creación centralizada de repositorios
- **Singleton Pattern**: Instancia única del RepositoryFactory
- **Facade Pattern**: Servicios que simplifican operaciones complejas
- **MVC Pattern**: Separación de lógica de presentación
- **Command Pattern**: Menús con acciones encapsuladas
- **DTO Pattern**: Transferencia de datos entre capas
- **Value Object Pattern**: Inmutabilidad de objetos de valor

---

## 🔧 Requisitos

- **Java Development Kit (JDK)**: 21+
- **PowerShell**: 5.1+ (Windows)
- **Dependencias** (descarga automática):
  - JUnit Platform Console Standalone 1.10.1
  - Mockito Core 5.8.0
  - Byte Buddy 1.14.11
  - Objenesis 3.3

---

## 📥 Instalación

### 1. Clonar el repositorio

```powershell
git clone https://github.com/margandona/wallet.git
cd wallet
```

### 2. Descargar dependencias

```powershell
.\download-dependencies.ps1
```

Este script descarga automáticamente:
- JUnit para testing
- Mockito para mocking en tests
- Dependencias necesarias (Byte Buddy, Objenesis)

### 3. Compilar el proyecto

```powershell
.\compile.ps1
```

Compila todo el código fuente y lo coloca en `target/classes/`.

---

## 🚀 Uso

### Ejecutar la aplicación

```powershell
.\run.ps1
```

### Interfaz de Usuario

El sistema presenta un menú interactivo de consola:

```
═══════════════════════════════════
  💳 SISTEMA DE BILLETERA DIGITAL
═══════════════════════════════════

   MENÚ PRINCIPAL
───────────────────────────────────
   1. 👤 Gestión de Usuarios
   2. 💰 Gestión de Cuentas
   3. 💸 Transacciones
   4. 🚪 Salir
───────────────────────────────────
```

### Flujo de Uso Típico

1. **Registrar Usuario**
   - Menú Usuarios → Registrar nuevo usuario
   - Ingresar: nombre, apellido, email, tipo documento, número

2. **Crear Cuenta**
   - Menú Cuentas → Crear nueva cuenta
   - Ingresar: email del usuario

3. **Depositar Dinero**
   - Menú Cuentas → Depositar dinero
   - Ingresar: número de cuenta, monto

4. **Realizar Transferencia**
   - Menú Transacciones → Realizar transferencia
   - Ingresar: cuenta origen, cuenta destino, monto

5. **Consultar Historial**
   - Menú Transacciones → Ver historial
   - Ingresar: número de cuenta

---

## 🧪 Testing

### Ejecutar todos los tests

```powershell
.\test.ps1
```

### Estadísticas de Testing

- **Total de Tests**: 85
- **Tests Exitosos**: 85 ✅
- **Cobertura**: ~85%

#### Distribución por Capa

| Capa              | Tests | Descripción                          |
|-------------------|-------|--------------------------------------|
| Domain            | 52    | Entities y Value Objects             |
| Application       | 14    | Use Cases con Mockito                |
| Infrastructure    | 15    | Repositories y servicios             |
| Integration       | 4     | Flujos end-to-end                    |

### Tecnologías de Testing

- **JUnit 5.10.1**: Framework de testing
- **Mockito 5.8.0**: Mocking de dependencias
- **PowerShell Scripts**: Automatización de tests

### Ejecutar tests específicos

```powershell
# Tests de dominio
java -jar lib\junit-platform-console-standalone-1.10.1.jar `
     --class-path "target\classes;target\test-classes;lib\*" `
     -c com.wallet.domain.entities.CuentaTest

# Tests de use cases
java -jar lib\junit-platform-console-standalone-1.10.1.jar `
     --class-path "target\classes;target\test-classes;lib\*" `
     -c com.wallet.application.usecases.CrearUsuarioUseCaseTest
```

---

## 📁 Estructura del Proyecto

```
wallet/
├── src/
│   ├── main/java/com/wallet/
│   │   ├── domain/                    # Capa de Dominio
│   │   │   ├── entities/              # Entidades (Usuario, Cuenta, Transaccion)
│   │   │   ├── valueobjects/          # Value Objects (Email, Dinero, etc.)
│   │   │   ├── exceptions/            # Excepciones de negocio
│   │   │   └── repositories/          # Interfaces de repositorios
│   │   ├── application/               # Capa de Aplicación
│   │   │   ├── usecases/              # Casos de uso (8 Use Cases)
│   │   │   ├── dtos/                  # Data Transfer Objects
│   │   │   │   ├── requests/          # DTOs de entrada
│   │   │   │   └── responses/         # DTOs de salida
│   │   │   └── mappers/               # Mappers Entity ↔ DTO
│   │   ├── infrastructure/            # Capa de Infraestructura
│   │   │   ├── repositories/          # Implementaciones en memoria
│   │   │   ├── services/              # Servicios de fachada
│   │   │   ├── factory/               # Factory de repositorios
│   │   │   └── logging/               # Sistema de logging
│   │   └── presentation/              # Capa de Presentación
│   │       ├── console/               # Interfaz de consola
│   │       ├── controllers/           # Controladores MVC
│   │       ├── menus/                 # Menús interactivos
│   │       └── Main.java              # Punto de entrada
│   └── test/java/com/wallet/          # Tests
│       ├── domain/                    # Tests de dominio (52)
│       ├── application/               # Tests de use cases (14)
│       └── infrastructure/            # Tests de infra (19)
├── .github/ # Configuración de GitHub Actions
│   └── workflows/ # Workflows de CI/CD
├── lib/ # Dependencias (JARs)
│   ├── junit-platform-console-standalone-1.10.1.jar
│   ├── mockito-core-5.8.0.jar
│   ├── byte-buddy-1.14.11.jar
│   ├── byte-buddy-agent-1.14.11.jar
│   └── objenesis-3.3.jar
├── target/                            # Archivos compilados
│   ├── classes/                       # Clases del proyecto
│   └── test-classes/                  # Clases de test
├── *.ps1                              # Scripts de PowerShell
├── README.md                          # Este archivo
├── ARCHITECTURE.md                    # Documentación de arquitectura
├── DEVELOPMENT.md                     # Guía de desarrollo
└── ETAPA_*.txt                        # Documentación de etapas
```

---

## 📚 Documentación

### API Generada (Javadoc)

La documentación técnica del código fuente está disponible en línea:

👉 [Explorar documentación en línea](https://kikeymod58.github.io/wallet--margadona/)

[![Javadoc](https://img.shields.io/badge/docs-Javadoc-green?logo=java&logoColor=white)](https://kikeymod58.github.io/wallet--margadona/)

#### Generar Javadoc localmente (opcional)

Si deseas generar la documentación en tu máquina:

##### Opción 1: Con JDK (recomendado)
Asegúrate de tener **JDK 21+** instalado y ejecuta en powershell:

```powershell
javadoc -d docs -sourcepath src/main/java -subpackages com.wallet -encoding UTF-8
```
Luego abre docs/index.html en tu navegador.

##### Opción 2: Con Maven (si lo tienes instalado)

```powershell
mvn javadoc:javadoc
```
La Javadoc se generará en target/reports/apidocs/index.html.

---

### Documentos Disponibles

- **[ARCHITECTURE.md](ARCHITECTURE.md)**: Arquitectura detallada y decisiones de diseño
- **[DEVELOPMENT.md](DEVELOPMENT.md)**: Guía para desarrolladores
- **[ETAPA_2_COMPLETADA.txt](ETAPA_2_COMPLETADA.txt)**: Documentación de la capa de dominio
- **[ETAPA_6_COMPLETADA.txt](ETAPA_6_COMPLETADA.txt)**: Documentación de testing

### API de Use Cases

#### Gestión de Usuarios

```java
// Crear usuario
CrearUsuarioRequest request = new CrearUsuarioRequest(
    "Juan", "Perez", "juan@email.com", 
    TipoDocumento.DNI, "12345678"
);
UsuarioDTO usuario = crearUsuarioUseCase.ejecutar(request);

// Buscar usuario por email
UsuarioDTO usuario = buscarUsuarioPorEmailUseCase.ejecutar("juan@email.com");

// Listar usuarios activos
List<UsuarioDTO> usuarios = listarUsuariosUseCase.ejecutar();
```

#### Gestión de Cuentas

```java
// Crear cuenta
CuentaDTO cuenta = crearCuentaUseCase.ejecutar(usuarioId);

// Consultar saldo
CuentaDTO cuenta = consultarSaldoUseCase.ejecutar(numeroCuenta);

// Listar cuentas de un usuario
List<CuentaDTO> cuentas = listarCuentasUseCase.ejecutar(usuarioId);
```

#### Operaciones Financieras

```java
// Depositar dinero
DepositarDineroRequest request = new DepositarDineroRequest(
    cuentaId, new BigDecimal("100.00"), "Depósito inicial"
);
TransaccionDTO transaccion = depositarDineroUseCase.ejecutar(request);

// Retirar dinero
RetirarDineroRequest request = new RetirarDineroRequest(
    cuentaId, new BigDecimal("50.00"), "Retiro cajero"
);
TransaccionDTO transaccion = retirarDineroUseCase.ejecutar(request);

// Transferir dinero
TransferirDineroRequest request = new TransferirDineroRequest(
    cuentaOrigenId, cuentaDestinoId, 
    new BigDecimal("200.00"), "Pago de servicio"
);
TransaccionDTO transaccion = transferirDineroUseCase.ejecutar(request);
```

---

## 🎯 Etapas del Proyecto

El proyecto se desarrolló en 8 etapas:

1. ✅ **Configuración**: Estructura de carpetas y scripts
2. ✅ **Domain Layer**: Entidades, Value Objects, excepciones
3. ✅ **Application Layer**: Use Cases, DTOs, Mappers
4. ✅ **Infrastructure Layer**: Repositorios, servicios, logging
5. ✅ **Presentation Layer**: UI de consola, controladores, menús
6. ✅ **Testing**: 85 tests unitarios, integración y e2e
7. ✅ **Documentación**: README, arquitectura, guías
8. ⏳ **Entrega**: Presentación y demostración final

---

## 🤝 Contribuir

### Convenciones de Código

- **Nombres de clases**: PascalCase (`Usuario`, `CuentaDTO`)
- **Nombres de métodos**: camelCase (`ejecutar()`, `buscarPorId()`)
- **Nombres de constantes**: UPPER_SNAKE_CASE (`MONEDA_DEFAULT`)
- **Paquetes**: minúsculas (`com.wallet.domain.entities`)

### Agregar un Nuevo Use Case

1. Crear la clase en `application/usecases/`
2. Implementar método `ejecutar()`
3. Crear DTOs de request/response si es necesario
4. Agregar tests en `test/.../usecases/`
5. Actualizar servicios en Infrastructure si aplica

### Ejecutar Tests Antes de Commit

```powershell
.\compile.ps1
.\test.ps1
```

---

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

---

## 👨‍💻 Autor

Desarrollado como proyecto educativo para demostrar:
- Principios SOLID
- Clean Architecture
- Patrones de Diseño
- Test Driven Development
- Buenas prácticas de Java

---

## 📞 Soporte

Para preguntas o problemas:
- Crear un issue en el repositorio
- Revisar la documentación en `/docs`
- Consultar los archivos `ETAPA_*.txt`

---

**¡Gracias por usar Wallet!** 💳✨
