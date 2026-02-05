    package com.example.DAO.Login_Register

    import Helpers.Database
    import com.example.Modelos.Auth.PersonaLogin
    import com.example.Modelos.USuario.Persona

    class AuthDAOImpl : AuthDAO {
        override fun login(credenciales: PersonaLogin): Persona? {
            val login = "SELECT * FROM usuarios WHERE dni = ? AND password = ?"
            Database.getConnection().use { connection ->
                connection?.prepareStatement(login).use { statement ->
                    statement?.setString(1, credenciales.dni)
                    statement?.setString(2, credenciales.password)

                    val rs = statement?.executeQuery()

                    if (rs?.next() == true) {
                        return Persona(
                            dni = rs.getString("dni"),
                            nombre = rs.getString("username"),
                            password  = rs.getString("password"),
                            fotoPerfil = rs.getString("foto_perfil"),
                            esUsuario = rs.getBoolean("es_usuario"),
                            esAdmin = rs.getBoolean("es_admin")
                        )
                    }
                }
            }
            return null
        }

    }