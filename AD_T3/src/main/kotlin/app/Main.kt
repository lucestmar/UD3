package app

import DAO.VideojuegosDAO
import model.Videojuego

fun main() {

    // Listar todas los videojuegos
    println("Lista de plantas:")
    VideojuegosDAO.listarVideojuegos().forEach {
        println(" - [${it.id}] ${it.titulo} (${it.genero}), año: ${it.anio} , nota: ${it.nota}")
    }

    // Consultar videojuegos por ID
    val videojuego = VideojuegosDAO.consultarVideojuegoPorId(3)
    if (videojuego != null) {
        println("Videojuego encontrada: [${videojuego.id}] ${videojuego.titulo} (${videojuego.genero}), año: ${videojuego.anio} , nota: ${videojuego.nota}€")
    } else {
        println("No se encontró ningun videojuegos con ese ID.")
    }

    // Insertar videojuegos nueva
    VideojuegosDAO.insertarVideojuegos(
        Videojuego(
            titulo = "Elden Ring",
            genero = "Souls-like",
            anio = 2022,
            nota = 9.6
        )
    )

    // Actualizar videojuego con id=1 (8.9)
    VideojuegosDAO.actualizarVideojuego(
        Videojuego(
            id = 5,
            titulo = "Celeste",
            genero = "Plataformas",
            anio = 2018,
            nota = 8.9
        )
    )

    // Eliminar videojuego con id=2 (Red Dead Redemption 2)
    VideojuegosDAO.eliminarVideojuego(2)
}