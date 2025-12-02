# 💰 Wallet - Sistema de Billetera Digital

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Sistema de billetera digital desarrollado en Java aplicando **Clean Architecture**, principios **SOLID**, las **4 Reglas del Diseño Simple** y buenas prácticas de POO.

## 📋 Descripción

Wallet es una aplicación de consola que permite gestionar cuentas bancarias digitales, realizar transacciones (depósitos, retiros, transferencias) y consultar el historial de movimientos. El proyecto está diseñado con énfasis en código limpio, arquitectura desacoplada y alta testeabilidad.

## 🏗️ Arquitectura

El proyecto sigue los principios de **Clean Architecture** con separación clara de responsabilidades:

```
wallet/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── wallet/
│   │               ├── domain/          # Lógica de negocio pura
│   │               │   ├── entities/    # Entidades del dominio
│   │               │   ├── valueobjects/  # Objetos de valor inmutables
│   │               │   ├── repositories/  # Interfaces de repositorios
│   │               │   └── exceptions/    # Excepciones del dominio
│   │               │
│   │               ├── application/     # Casos de uso
│   │               │   ├── usecases/    # Implementación de casos de uso
│   │               │   ├── dtos/        # Data Transfer Objects
│   │               │   └── services/    # Interfaces de servicios
│   │               │
│   │               ├── infrastructure/  # Implementaciones técnicas
│   │               │   ├── repositories/  # Implementaciones de repos
│   │               │   ├── persistence/   # Gestión de persistencia
│   │               │   └── services/      # Servicios externos
│   │               │
│   │               └── presentation/    # Interfaz de usuario
│   │                   ├── controllers/  # Controladores
│   │                   └── ui/           # Interfaz de consola
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── wallet/
│                   ├── domain/          # Tests de dominio
│                   ├── application/     # Tests de casos de uso
│                   └── infrastructure/  # Tests de infraestructura
│
├── pom.xml
├── README.md
└── PLAN_DESARROLLO.md
```

## 🎯 Principios Aplicados

### SOLID
- **S**ingle Responsibility Principle: Cada clase tiene una única razón para cambiar
- **O**pen/Closed Principle: Abierto a extensión, cerrado a modificación
- **L**iskov Substitution Principle: Las implementaciones son intercambiables
- **I**nterface Segregation Principle: Interfaces específicas y cohesivas
- **D**ependency Inversion Principle: Dependencias hacia abstracciones

### Las 4 Reglas del Diseño Simple
1. ✅ Pasa todos los tests
2. 📖 Revela intención (código autodocumentado)
3. 🚫 Sin duplicación (DRY)
4. 🎯 Mínimo de elementos (YAGNI)

## 🚀 Tecnologías

- **Java 17**: Lenguaje de programación
- **Maven**: Gestión de dependencias y build
- **JUnit 5**: Framework de testing
- **Mockito**: Framework de mocking
- **AssertJ**: Assertions fluidas y legibles
- **JaCoCo**: Cobertura de código

## 📦 Instalación

### Requisitos Previos
- JDK 17 o superior (el proyecto usa Java 21)
- Maven 3.8 o superior (opcional - incluye scripts PowerShell alternativos)

### Clonar el Repositorio
```bash
git clone <repository-url>
cd wallet
```

### Opción A: Con Maven (Recomendado)

#### Compilar el Proyecto
```bash
mvn clean compile
```

#### Ejecutar Tests
```bash
mvn test
```

#### Generar Reporte de Cobertura
```bash
mvn clean test jacoco:report
```
El reporte se generará en: `target/site/jacoco/index.html`

#### Empaquetar la Aplicación
```bash
mvn clean package
```

#### Ejecutar la Aplicación
```bash
java -jar target/wallet-app-1.0.0.jar
```

O directamente con Maven:
```bash
mvn exec:java -Dexec.mainClass="com.wallet.Main"
```

### Opción B: Sin Maven (Scripts PowerShell)

Si no tienes Maven instalado, puedes usar los scripts de PowerShell incluidos:

#### Compilar
```powershell
.\compile.ps1
```

#### Ejecutar
```powershell
.\run.ps1
```

#### Compilar y Ejecutar (Todo en uno)
```powershell
.\build-and-run.ps1
```

> 💡 **Nota**: Los scripts PowerShell son una alternativa simple para desarrollo. Para producción o CI/CD, se recomienda usar Maven.

Ver más detalles en [SCRIPTS.md](SCRIPTS.md)

## 🎮 Uso

La aplicación presenta un menú interactivo de consola con las siguientes opciones:

1. **Crear Usuario**: Registrar un nuevo usuario en el sistema
2. **Crear Cuenta**: Asociar una cuenta bancaria a un usuario
3. **Realizar Depósito**: Agregar fondos a una cuenta
4. **Realizar Retiro**: Extraer fondos de una cuenta
5. **Realizar Transferencia**: Transferir fondos entre cuentas
6. **Consultar Saldo**: Ver el saldo actual de una cuenta
7. **Ver Historial**: Consultar movimientos realizados
8. **Salir**: Cerrar la aplicación

## 🧪 Testing

El proyecto mantiene una alta cobertura de tests:

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn clean test jacoco:report

# Ejecutar tests específicos
mvn test -Dtest=UsuarioTest
```

## 📊 Cobertura de Código

Objetivo de cobertura mínima: **70%**

Para ver el reporte de cobertura:
1. Ejecutar: `mvn clean test jacoco:report`
2. Abrir: `target/site/jacoco/index.html`

## 🏆 Buenas Prácticas Implementadas

- ✅ Nombres descriptivos y significativos
- ✅ Métodos pequeños y enfocados (< 20 líneas)
- ✅ Inmutabilidad en Value Objects
- ✅ Validaciones tempranas (Fail-fast)
- ✅ Separación de responsabilidades
- ✅ Tests unitarios y de integración
- ✅ Commits atómicos con mensajes descriptivos
- ✅ Documentación clara y actualizada

## 📝 Convenciones de Código

- **Paquetes**: lowercase (com.wallet.domain)
- **Clases**: PascalCase (Usuario, CuentaBancaria)
- **Métodos**: camelCase (realizarDeposito, obtenerSaldo)
- **Constantes**: UPPER_SNAKE_CASE (MAX_RETIRO_DIARIO)
- **Variables**: camelCase (saldoActual, nombreUsuario)

## 🔄 Estado del Proyecto

### ✅ Completado
- [x] **Etapa 1**: Configuración inicial y estructura del proyecto
  - ✅ Estructura de carpetas con Clean Architecture
  - ✅ Maven configurado con dependencias
  - ✅ Scripts PowerShell alternativos
  - ✅ Documentación inicial
  
- [x] **Etapa 2**: Implementación de la capa de dominio
  - ✅ 3 Entidades (Usuario, Cuenta, Transaccion)
  - ✅ 4 Value Objects (Email, DocumentoIdentidad, Dinero, TipoTransaccion)
  - ✅ 3 Interfaces de Repositorio (IUsuarioRepository, ICuentaRepository, ITransaccionRepository)
  - ✅ 4 Excepciones de dominio
  - ✅ 52 Tests unitarios (100% exitosos, >95% coverage)

### 🚧 En Progreso
- [ ] **Etapa 3**: Implementación de la capa de aplicación

### 📋 Pendiente
- [ ] Etapa 4: Implementación de la capa de infraestructura
- [ ] Etapa 5: Implementación de la capa de presentación
- [ ] Etapa 6: Testing integral
- [ ] Etapa 7: Documentación y refinamiento
- [ ] Etapa 8: Entrega y presentación

Ver detalles completos en [PLAN_DESARROLLO.md](PLAN_DESARROLLO.md)

**Progreso General**: ⬛⬛⬜⬜⬜⬜⬜⬜ 25% (2/8 etapas completadas)

## 👥 Contribución

Este es un proyecto educativo. Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Contacto

Proyecto desarrollado como evaluación integradora del Módulo 2.

---

⭐ **Star** este proyecto si te ha sido útil!
