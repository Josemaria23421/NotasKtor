package com.example.DAO.Usuario

import Helpers.Database
import com.example.Modelos.USuario.Persona
import java.util.ArrayList

class PersonaDAOImpl : PersonaDAO {
    override fun obtenerPorDni(dni: String): Persona? {
        val dniDeLaPersona = "SELECT * FROM USUARIOS WHERE DNI = ?"
        return Database.getConnection()?.use { conexion ->
            conexion.prepareStatement(dniDeLaPersona).use { statement ->
                statement.setString(1, dni)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        Persona(
                            dni = resultSet.getString("dni"),
                            nombre = resultSet.getString("username"),
                            password = resultSet.getString("password"),
                            esUsuario = resultSet.getBoolean("es_usuario"),
                            esAdmin = resultSet.getBoolean("es_admin"),
                            fotoPerfil = resultSet.getString("foto_perfil")
                        )
                    } else {
                        null
                    }
                }
            }
        }
    }


    override fun actualizarContrasena(dni: String, nuevaClave: String): Boolean {
        val consultaActualizarPassword = "UPDATE USUARIOS SET password = ? WHERE dni = ?"

        return Database.getConnection()?.use { Conexion ->
            Conexion.prepareStatement(consultaActualizarPassword).use { statement ->
                statement.setString(1, nuevaClave)
                statement.setString(2, dni)
                val filasModificadas = statement.executeUpdate()
                filasModificadas > 0
            }
        } ?: false
    }

    override fun actualizarFoto(dni: String, urlFoto: String): Boolean {
        val modificarFotoDePerfil = "UPDATE USUARIOS SET foto_Perfil = ? WHERE dni = ?"

        return try {
            Database.getConnection().use { connection ->
                connection?.prepareStatement(modificarFotoDePerfil).use { statement ->
                    statement?.setString(1, urlFoto)
                    statement?.setString(2, dni)

                    val filasAfectadas = statement?.executeUpdate() ?: 0
                    filasAfectadas > 0
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun crearUsuario(persona: Persona): Boolean {
        var buscarUsuario = "select dni FROM usuarios where dni = ?"
        var sqlINsertarUsuarios = "INSERT INTO USUARIOS (USERNAME, PASSWORD, FOTO_PERFIL, ES_USUARIO, ES_ADMIN, DNI) VALUES (?,?,?,?,?,?)"
        try {
            Database.getConnection().use { connection ->
                //Primer Buscamos que exista
                connection?.prepareStatement(buscarUsuario).use { statement ->
                    statement?.setString(1, persona.dni)
                    val rs = statement?.executeQuery()
                    if (rs?.next() == true) {
                        println("Usuario ya existente ${persona.dni}")
                        return false
                    }
                    //En caso de que no exista ->
                    else {
                        connection?.prepareStatement(sqlINsertarUsuarios).use { statement ->
                            statement?.setString(1, persona.nombre)
                            statement?.setString(2, persona.password)
                            statement?.setString(3, persona.fotoPerfil)
                            statement?.setBoolean(4, persona.esUsuario)
                            statement?.setBoolean(5, persona.esAdmin)
                            statement?.setString(6, persona.dni)
                            statement?.executeUpdate()

                        }
                        return true
                    }
                }

            }
        } catch (e: Exception) {
            println("Error al registrar o asignar rol: ${e.message}")
            e.printStackTrace()
        }
        return false
    }

    override fun borrarUsuario(dni: String): Boolean {
        var cargarseUsuario = "DELETE FROM USUARIOS WHERE DNI = ?"
        return Database.getConnection()?.use { Conexion ->
            Conexion.prepareStatement(cargarseUsuario).use { statement ->
                statement.setString(1, dni)
                val filasEliminadas = statement.executeUpdate()
                filasEliminadas > 0
            }
        } ?: false
    }

    override fun actualizarRoles(
        dni: String,
        esUsuario: Boolean,
        esAdmin: Boolean
    ): Boolean {
        val consultaModificarRolesDeUsuario = "UPDATE USUARIOS SET es_usuario = ?, es_admin = ? WHERE dni = ?"

        return Database.getConnection()?.use { Conexion ->
            Conexion.prepareStatement(consultaModificarRolesDeUsuario).use { statement ->
                statement.setBoolean(1, esUsuario)
                statement.setBoolean(2, esAdmin)
                statement.setString(3, dni)
                val exitoActualizacion = statement.executeUpdate()
                exitoActualizacion > 0
            }
        } ?: false
    }

    override fun listarTodosLosUsuarios(): List<Persona> {
        val listado = "SELECT * FROM USUARIOS"
        var personas = ArrayList<Persona>()
        Database.getConnection()?.use { conexion ->
            conexion.prepareStatement(listado).use { statement ->
                statement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        personas.add(
                            Persona(
                                dni = resultSet.getString("dni"),
                                nombre = resultSet.getString("username"),
                                password = resultSet.getString("password"),
                                esUsuario = resultSet.getBoolean("es_usuario"),
                                esAdmin = resultSet.getBoolean("es_admin"),
                                fotoPerfil = resultSet.getString("foto_perfil")
                            )
                        )
                    }
                }
            }
        }
        return personas
    }
}
