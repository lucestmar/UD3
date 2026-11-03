package DAO

import database.ConexionBD
import model.Videojuego

object VideojuegosDAO {

    fun listarVideojuegos(): List<Videojuego> {
        val lista = mutableListOf<Videojuego>()
        val sql = "SELECT * FROM videojuegos"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    lista.add(Videojuego(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        genero = rs.getString("genero"),
                        anio = rs.getInt("anio"),
                        nota = rs.getDouble("nota")
                    ))
                }
            }
        }
        return lista
    }

    fun consultarVideojuegoPorId(id: Int): Videojuego? {
        var videojuego: Videojuego? = null
        val sql = "SELECT * FROM videojuegos WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    videojuego = Videojuego(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        genero = rs.getString("genero"),
                        anio = rs.getInt("anio"),
                        nota = rs.getDouble("nota")
                    )
                }
            }
        }
        return videojuego
    }

    fun insertarVideojuegos(videojuego: Videojuego) {
        val sql = "INSERT INTO videojuegos(titulo, genero, anio, nota) VALUES (?, ?, ?, ?)"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videojuego.titulo)
                stmt.setString(2, videojuego.genero)
                stmt.setInt(3, videojuego.anio)
                stmt.setDouble(4, videojuego.nota)
                if (stmt.executeUpdate() > 0) {
                    println("${videojuego.titulo} insertada con éxito.")
                }
            }
        }
    }

    fun actualizarVideojuego(videojuego: Videojuego) {
        if (videojuego.id == null) {
            println("Error: No se puede actualizar una videojuego sin ID.")
            return
        }
        val sql = "UPDATE videojuego SET titulo = ?, genero = ?, anio = ?, nota = ? WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videojuego.titulo)
                stmt.setString(2, videojuego.genero)
                stmt.setInt(3, videojuego.anio)
                stmt.setDouble(4, videojuego.nota)
                stmt.setInt(5, videojuego.id)
                if (stmt.executeUpdate() > 0) {
                    println("Videojuego con ID ${videojuego.id} actualizada.")
                } else {
                    println("No se encontró ninguna videojuego con ID ${videojuego.id} para actualizar.")
                }
            }
        }
    }

    fun eliminarVideojuego(id: Int) {
        val sql = "DELETE FROM videojuego WHERE id = ?"
        ConexionBD.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                if (stmt.executeUpdate() > 0) {
                    println("Videojuego con ID $id eliminada.")
                } else {
                    println("No se encontró ningun videojuego con ID $id para eliminar.")
                }
            }
        }
    }
}
