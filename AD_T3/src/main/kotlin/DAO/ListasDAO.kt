package DAO

import database.ConexionBD
import model.Lista

object ListasDAO {
    fun crearLista(lista: Lista) {
        val sql = "INSERT INTO listas (nombre, id_jugador) VALUES (?, ?)"

        ConexionBD.getConnection().use { conn ->
            conn?.prepareStatement(sql).use { stmt ->
                stmt?.setString(1, lista.nombre)
                stmt?.setInt(2, lista.idJugador)
                stmt?.executeUpdate()
            }
        }
    }

    fun eliminarLista(id: Int) {
        val sql = "DELETE FROM listas WHERE id_lista = ?"

        ConexionBD.getConnection().use { conn ->
            conn?.prepareStatement(sql).use { stmt ->
                stmt?.setInt(1, id)
                val filasAfectadas = stmt?.executeUpdate()

                if (filasAfectadas == 0) {
                    println("No se encontró ninguna lista con el ID $id.")
                }
            }
        }
    }

}