package DAO

import database.ConexionBD
import model.Videojuego
import java.sql.Connection
import java.sql.Date
import java.sql.SQLException
import java.time.LocalDate
import kotlin.use

object ListaVideojuegoDAO {
    fun anyadirALista(id_lista: Int, id_videojuego: Int) {
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
                    stmt?.setDate(3, Date.valueOf(LocalDate.now()))
                    stmt?.executeUpdate()
                }

            // Confirmar cambios
            conn?.commit()
            println("Transacción realizada con éxito.")
        } catch (e: SQLException) {
            conn?.rollback()
            if (e.sqlState == "23505") {
                println("Intento de insertar clave duplicada. Transacción revertida.")
            } else {
                println("Error SQL (${e.sqlState}): ${e.message}")
                throw e
            }
        } finally {
            // Código que se ejecuta siempre
            println("Fin del programa.")
        }
    }

    fun listarVideojuegosDeLista(idLista: Int): List<Videojuego> {
            val listaVideojuegos = mutableListOf<Videojuego>()
            val sql = """
        SELECT v.id, v.titulo, v.genero, v.anio, v.nota 
        FROM videojuegos v
        INNER JOIN listas_videojuegos lv ON v.id = lv.id_videojuego
        WHERE lv.id_lista = ?
         """.trimIndent()

            ConexionBD.getConnection().use { conn ->
                conn?.prepareStatement(sql).use { stmt ->
                    stmt?.setInt(1, idLista)
                    stmt?.executeQuery()?.use { rs ->
                        while (rs.next()) {
                            listaVideojuegos.add(
                                Videojuego(
                                    id = rs.getInt("id"),
                                    titulo = rs.getString("titulo") ?: "",
                                    genero = rs.getString("genero") ?: "",
                                    anio = rs.getInt("anio"),
                                    nota = rs.getDouble("nota")
                                )
                            )
                        }
                    }
                }
            }

            return listaVideojuegos
    }

}