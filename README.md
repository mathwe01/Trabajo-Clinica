# Trabajo-Clinica — Historias clínicas digitales (Centro de Salud Ganímedes)

Prototipo en **Java (OpenJDK) + Swing + MySQL (JDBC)** para registrar pacientes, historias clínicas y atenciones.
Curso: Lenguajes de Programación (UAIN1288P) — Grupo 2.

## Paradigmas y patrones
- **Orientado a objetos:** clases `Paciente`, `HistoriaClinica`, `Atencion` con encapsulamiento.
- **Orientado a eventos:** formularios Swing (`RegistroPacienteForm`, `LoginForm`).
- **Funcional:** `ServicioPacientes` usa `filter`, `map` y `reduce` con lambdas.
- **Patrones:** Singleton (`Conexion`), DAO (`dao/`), Factory Method (`PacienteFactory`).
- **SOLID (inversión de dependencias):** `ServicioPacientes` depende de `IPacienteDAO`, no de MySQL.

## Estructura
```
src/Conexion   conexión Singleton a MySQL
src/dao        interfaces (I*DAO) y DAO con MySQL
src/fabrica    PacienteFactory
src/modelo     clases del dominio
src/seguridad  hash de contraseñas (PBKDF2 con sal)
src/servicio   funciones de orden superior
src/Vista      formularios Swing
src/sql        schema_basedatos.sql
test           pruebas JUnit 5
```

## Cómo ejecutarlo
1. Instalar JDK 17 o superior, MySQL y NetBeans.
2. Ejecutar `src/sql/schema_basedatos.sql` en MySQL (crea la base `bd_centrosaludganimedes`).
3. En `src/Conexion/Conexion.java` poner la contraseña de tu MySQL local.
4. Agregar el driver JDBC de MySQL (`mysql-connector-j`) a las librerías del proyecto.
5. Ejecutar `Vista/RegistroPacienteForm` o `Vista/LoginForm`.

## Pruebas
- Requieren JUnit 5 (`junit-platform-console-standalone`) en las librerías.
- `ServicioPacientesTest`, `SeguridadClaveTest` y `PacienteFactoryTest` no necesitan MySQL.
- `PacienteDAOTest`, `HistoriaClinicaDAOTest` y `AtencionDAOTest` son de integración: necesitan MySQL encendido y la base creada. Cada prueba borra sus datos al terminar.
- El mensaje "Duplicate entry" en consola es esperado (prueba de documento repetido).

## Protección de datos (Ley N.° 29733)
Las contraseñas se guardan con hash y sal; el DNI se enmascara al mostrarse. Los datos de ejemplo son ficticios.
