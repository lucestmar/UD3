package app

import DAO.JugadorDAO
import DAO.ListaVideojuegoDAO
import DAO.ListasDAO
import DAO.VideojuegosDAO
import model.Jugador
import model.Lista
import model.Videojuego

fun main() {
    var opcion: Int

    do {
        println(
            """
            ===== CATÁLOGO DE VIDEOJUEGOS =====

            --- VIDEOJUEGOS ---
            1. Ver todos los videojuegos
            2. Buscar videojuego por ID
            3. Añadir videojuego
            4. Modificar videojuego
            5. Eliminar videojuego

            --- JUGADORES ---
            6. Crear jugador
            7. Eliminar jugador

            --- LISTAS ---
            8. Crear lista
            9. Eliminar lista
            10. Añadir videojuego a una lista
            11. Ver videojuegos de una lista

            0. Salir
            ====================================
            """.trimIndent()
        )
        print("Escoge una opción: ")
        opcion = readln().toIntOrNull() ?: -1

        when (opcion) {
            1 -> {
                println("\nLista de videojuegos:")
                VideojuegosDAO.listarVideojuegos().forEach {
                    println(" - [${it.id}] ${it.titulo} (${it.genero}), año: ${it.anio}, nota: ${it.nota}")
                }
            }

            2 -> {
               encontrarVideojuego()
            }

            3 -> {
                añadirJuego()
            }

            4 -> {
               modificar()
            }

            5 -> {
                print("ID del videojuego a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                VideojuegosDAO.eliminarVideojuego(id)
                println("Operación de eliminación ejecutada.")
            }

            6 -> {
                print("Nombre del jugador: ")
                val nombre = readln()
                JugadorDAO.insertarJugador(Jugador(nombre = nombre))
                println("Jugador creado.")
            }

            7 -> {
                print("ID del jugador a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                JugadorDAO.eliminarJugador(id)
                println("Jugador eliminado.")
            }

            8 -> {
                print("Nombre de la lista: ")
                val nombre = readln()
                print("ID del jugador propietario: ")
                val idJugador = readln().toIntOrNull() ?: 0

                ListasDAO.crearLista(Lista(idLista = 0 , nombre = nombre, idJugador = idJugador))
                println("Lista creada.")
            }

            9 -> {
                print("ID de la lista a eliminar: ")
                val id = readln().toIntOrNull() ?: 0
                ListasDAO.eliminarLista(id)
                println("Lista eliminada.")
            }

            10 -> {
                print("ID de la lista: ")
                val idLista = readln().toIntOrNull() ?: 0
                print("ID del videojuego: ")
                val idVideojuego = readln().toIntOrNull() ?: 0
                ListaVideojuegoDAO.anyadirALista(idLista, idVideojuego)
                println("Videojuego añadido a la lista.")
            }

            11 -> {
                print("ID de la lista a consultar: ")
                val idLista = readln().toIntOrNull() ?: 0
                println("Videojuegos en la lista $idLista:")
                ListaVideojuegoDAO.listarVideojuegosDeLista(idLista).forEach {
                    println(" - [${it.id}] ${it.titulo} (${it.genero})")
                }
            }

            0 -> println("Saliendo del programa...")

            else -> println("Opción no válida. Inténtalo de nuevo.")
        }

        println()
    } while (opcion != 0)
}

fun modificar() {
    print("ID del videojuego a modificar: ")
    val id = readln().toIntOrNull() ?: 0
    print("Nuevo título: ")
    val titulo = readln()
    print("Nuevo género: ")
    val genero = readln()
    print("Nuevo año: ")
    val anio = readln().toIntOrNull() ?: 2000
    print("Nueva nota: ")
    val nota = readln().toDoubleOrNull() ?: 0.0

    VideojuegosDAO.actualizarVideojuego(
        Videojuego(id = id, titulo = titulo, genero = genero, anio = anio, nota = nota)
    )
    println("Videojuego actualizado.")}

fun añadirJuego() {
    print("Título: ")
    val titulo = readln()
    print("Género: ")
    val genero = readln()
    print("Año: ")
    val anio = readln().toIntOrNull() ?: 2000
    print("Nota: ")
    val nota = readln().toDoubleOrNull() ?: 0.0

    VideojuegosDAO.insertarVideojuegos(
        Videojuego(titulo = titulo, genero = genero, anio = anio, nota = nota)
    )
    println("Videojuego añadido correctamente.")
}

fun encontrarVideojuego() {
    print("Introduce el ID del videojuego: ")
    val id = readln().toIntOrNull() ?: 0
    val videojuego = VideojuegosDAO.consultarVideojuegoPorId(id)
    if (videojuego != null) {
        println("Videojuego encontrado: [${videojuego.id}] ${videojuego.titulo} (${videojuego.genero}), año: ${videojuego.anio}, nota: ${videojuego.nota}")
    } else {
        println("No se encontró ningún videojuego con ese ID.")
    }
}
