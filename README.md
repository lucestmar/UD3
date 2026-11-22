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
)'''
