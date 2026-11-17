package DAO

import database.ConexionBD
import java.sql.Connection
import java.sql.SQLException

object ListasDAO {
    fun anyadirALista(id_lista: Int, id_videojuego: Int, fecha_añadido: String) {
        var conn: Connection? = null
        try {
            conn = ConexionBD.getConnection()
            conn?.autoCommit = false

            //Ver que el videojuego existe antes de añadirlo
            var existe = false
            conn?.prepareStatement("SELECT id FROM videojuegos WHERE id = ?")?.use { stmt ->
                stmt.setInt(1, id_videojuego)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    existe = true
                }
            }

            if (!existe) {
                println("Error: No existe ningún videojuego con ID $id_videojuego.")
                conn?.rollback()
                return
            }

            // Añadir línea en tabla listas_videojuegos
            conn?.prepareStatement("INSERT INTO listas_videojuegos(id_lista, id_videojuego, fecha_añadido) VALUES (?, ?, ?)")
                .use { stmt ->
                    stmt?.setInt(1, id_lista)
                    stmt?.setInt(2, id_videojuego)
                    stmt?.setString(3, fecha_añadido)
                    stmt?.executeUpdate()
                }

            // Confirmar cambios
            conn?.commit()
            println("Transacción realizada con éxito.")
        } catch (e: SQLException) {
            if (e.message?.contains("UNIQUE constraint failed") == true) {
                println("Intento de insertar clave duplicada")
                conn?.rollback()
                println("Transacción revertida.")
            } else {
                throw e // otros errores, relanzamos
            }
        } finally {
            // Código que se ejecuta siempre
            println("Fin del programa.")
        }
    }
}