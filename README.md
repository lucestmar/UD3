# Catálogo de Videojuegos

Programa de consola en Kotlin para gestionar un catálogo de videojuegos.
Permite administrar videojuegos, jugadores y listas personalizadas mediante acceso a datos con JDBC sobre una base de datos relacional PostgreSQL.

## 1. Estructura de datos

### **Data Class:**

```kotlin
data class Videojuego(
    val id: Int = 0,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val nota: Double
)

data class Jugador(
    val id: Int = 0,
    val nombre: String
)

data class Lista(
    val idLista: Int = 0,
    val nombre: String,
    val idJugador: Int
)
```
## 2. Instrucciones de ejecución

- **Requisitos previos**: JDK 17 o superior instalado y servidor PostgreSQL activo en `localhost`.
- **Compilación**: Abre el proyecto en IntelliJ IDEA y deja que Gradle sincronice las dependencias (driver JDBC de PostgreSQL).
- **Base de datos inicial**: Crea la base de datos `videojuegos` y ejecuta el script SQL para crear las 4 tablas (`videojuegos`, `jugadores`, `listas`, `listas_videojuegos`) y poblar los datos iniciales antes de ejecutar.
- **Ejecución principal**: Ejecuta `Main.kt` para abrir el menú interactivo en consola y gestionar videojuegos, jugadores y listas.

## 3. Decisiones de diseño

- Elegí PostgreSQL con JDBC y el patrón DAO (`VideojuegosDAO`, `JugadorDAO`, `ListasDAO`, `ListaVideojuegoDAO`) para separar las consultas SQL de la lógica del menú.
- Implementé claves foráneas con `ON DELETE CASCADE` en `listas` y `listas_videojuegos` para que al eliminar un jugador o una lista se borren automáticamente sus registros asociados sin errores de integridad.
- Utilicé una clave primaria compuesta (`id_lista`, `id_videojuego`) en la tabla intermedia para evitar que se añada el mismo videojuego dos veces a la misma lista.
