# Taller 2: Principios SOLID — Gestión de Usuarios

**Laboratorio de Ingeniería del Software II — Universidad del Cauca (2-2026)**

Aplicación de escritorio en Java Swing que implementa la **HU01 — Gestión de Usuarios del Sistema** del proyecto "Sistema para la Gestión, Validación y Administración de un Banco de Preguntas para la Preparación de las Pruebas Saber Pro".

## Funcionalidades

- **Registro de usuarios**: con validación de contraseña (mín. 6 caracteres, 1 mayúscula, 1 dígito, 1 carácter especial) y hashing con Argon2id.
- **Autenticación (login)**: verificación contra hash almacenado, rechazo de usuarios inactivos, mensajes de error genéricos.
- **Menú por rol**: Administrador accede a gestión de usuarios (listar/activar/inactivar); otros roles ven opciones placeholder de futuras HU.
- **Persistencia en SQLite**: archivo `usuarios.db` creado automáticamente.

## Arquitectura

```
com.unicauca.taller2.usuarios
│
├── domain/                          ← Entidades y enums del dominio
│   ├── Usuario.java                 (entidad de dominio)
│   ├── Rol.java                     (enum: ADMINISTRADOR, AUTOR_PREGUNTAS, REVISOR, DOCENTE, ESTUDIANTE)
│   └── EstadoUsuario.java           (enum: ACTIVO, INACTIVO)
│
├── domain/port/                     ← Interfaces (puertos) — Inversión de Dependencias
│   ├── UsuarioRepository.java       (guardar, buscar, listar, actualizar estado)
│   ├── PasswordHasher.java          (hash + verify)
│   ├── PasswordPolicy.java          (validar reglas de contraseña)
│   └── PasswordPolicyException.java
│
├── application/                     ← Servicios de aplicación (casos de uso)
│   ├── UsuarioService.java          (registrar, listar, cambiar estado)
│   ├── AutenticacionService.java    (login)
│   └── AutenticacionException.java
│
├── infrastructure/                  ← Implementaciones concretas
│   ├── persistence/
│   │   ├── ConexionSQLite.java      (fábrica de conexión JDBC, crea esquema)
│   │   └── UsuarioRepositorySQLite.java (implementa UsuarioRepository)
│   └── security/
│       ├── Argon2PasswordHasher.java    (implementa PasswordHasher con Argon2id)
│       └── PasswordPolicyDefault.java   (implementa PasswordPolicy)
│
├── presentation/swing/              ← Interfaz gráfica (Swing)
│   ├── LoginFrame.java
│   ├── RegistroFrame.java
│   ├── MenuAdministradorFrame.java
│   ├── MenuGenericoFrame.java       (reutilizable para todos los roles no-admin)
│   └── GestionUsuariosPanel.java    (listar/activar/inactivar usuarios)
│
└── Main.java                        ← COMPOSITION ROOT (único lugar con new de infrastructure)
```

## Principios SOLID Aplicados

### SRP — Principio de Responsabilidad Única
Cada clase tiene una única responsabilidad claramente definida:
- `Usuario.java` → solo datos de la entidad
- `PasswordPolicyDefault` → solo validar reglas de contraseña
- `Argon2PasswordHasher` → solo hashear/verificar contraseñas
- `UsuarioRepositorySQLite` → solo persistencia JDBC
- `UsuarioService` → solo orquestar casos de uso de gestión
- `AutenticacionService` → solo autenticación
- Cada Frame/Panel de Swing → solo capturar datos y delegar al servicio

### OCP — Principio Abierto/Cerrado
- Se puede añadir un nuevo algoritmo de hashing (p.ej. `BcryptPasswordHasher`) sin modificar `UsuarioService` ni `AutenticacionService`.
- Se puede crear `PasswordPolicyStrict` con reglas más exigentes sin tocar los servicios.
- `MenuGenericoFrame` usa un `Map<Rol, List<String>>` para definir opciones por rol: añadir opciones a un rol solo requiere agregar entradas al mapa.

### LSP — Principio de Sustitución de Liskov
- Cualquier implementación de `UsuarioRepository` (SQLite, en memoria, mock) es sustituible sin romper los servicios.
- Los tests unitarios lo demuestran: usan mocks de Mockito en lugar de las implementaciones reales, y los servicios funcionan igual.

### ISP — Principio de Segregación de Interfaces
- En lugar de una interfaz monolítica `UsuarioDAO`, se separan 3 interfaces cohesivas:
  - `UsuarioRepository` (persistencia)
  - `PasswordHasher` (hashing)
  - `PasswordPolicy` (validación de políticas)
- Cada servicio depende solo de las interfaces que necesita.

### DIP — Principio de Inversión de Dependencias
- Las capas `application` y `presentation` **nunca** importan clases de `infrastructure`.
- Dependen exclusivamente de las interfaces en `domain.port`.
- `Main.java` (composition root) es el **único** lugar donde se instancian las implementaciones concretas y se inyectan por constructor.

## Requisitos Previos

- **Java 17+** (probado con Java 23)
- No requiere Maven instalado (incluye Maven Wrapper `mvnw`)

## Compilar

```bash
# Windows
.\mvnw.cmd clean package

# Linux/Mac
./mvnw clean package
```

## Ejecutar

```bash
# Opción 1: Con Maven
.\mvnw.cmd compile exec:java -Dexec.mainClass="com.unicauca.taller2.usuarios.Main"

# Opción 2: JAR ejecutable (después de package)
java -jar target/taller2-usuarios-1.0-SNAPSHOT.jar
```

## Ejecutar Pruebas Unitarias

```bash
.\mvnw.cmd test
```

**28 pruebas unitarias** organizadas en 3 clases:
- `UsuarioServiceTest` (8 tests): registro válido, rechazo por política de contraseña, rechazo por duplicado, cambio de estado.
- `AutenticacionServiceTest` (5 tests): login exitoso, fallo por usuario inexistente, fallo por contraseña incorrecta, fallo por usuario inactivo.
- `PasswordPolicyDefaultTest` (15 tests): casos válidos parametrizados, casos inválidos por cada regla, casos límite.

## Dependencias

| Librería | Versión | Propósito |
|---|---|---|
| `org.xerial:sqlite-jdbc` | 3.47.1.0 | Driver JDBC para SQLite |
| `de.mkammerer:argon2-jvm` | 2.11 | Hashing de contraseñas con Argon2id |
| `org.junit.jupiter:junit-jupiter` | 5.11.4 | Framework de pruebas unitarias |
| `org.mockito:mockito-core` | 5.14.2 | Dobles de prueba (mocks) |

## Base de Datos

SQLite (`usuarios.db`), creada automáticamente al iniciar la aplicación.

```sql
CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre_usuario TEXT UNIQUE NOT NULL,
    nombre_completo TEXT NOT NULL,
    rol TEXT NOT NULL,
    estado TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    fecha_creacion TEXT NOT NULL
);
```

## Autor

Taller 2 — Laboratorio de Ingeniería del Software II, Universidad del Cauca, periodo 2-2026.
