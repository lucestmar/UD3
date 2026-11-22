package DAO

import database.ConexionBD
import model.Jugador

object JugadorDAO {
    fun insertarJugador(jugador: Jugador) {
        val sql = "INSERT INTO jugador (nombre) VALUES (?)"

        ConexionBD.getConnection().use { conn ->
            conn?.prepareStatement(sql).use { stmt ->
                stmt?.setString(1, jugador.nombre)
                stmt?.executeUpdate()
            }
        }
    }

    fun eliminarJugador(id: Int) {
        val sql = "DELETE FROM jugador WHERE id = ?"

        ConexionBD.getConnection().use { conn ->
            conn?.prepareStatement(sql).use { stmt ->
                stmt?.setInt(1, id)
                val filasAfectadas = stmt?.executeUpdate()

                if (filasAfectadas == 0) {
                    println("No se encontró ningún jugador con el ID $id.")
                }
            }
        }
    }
}