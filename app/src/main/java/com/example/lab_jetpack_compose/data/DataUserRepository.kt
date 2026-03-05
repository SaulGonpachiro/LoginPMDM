/**
 * DataUserRepository  [DATOS DE PRUEBA - NO SE USA EN PRODUCCIÓN]
 *
 * Implementación en memoria del UserRepository.
 * Contiene una lista de usuarios de prueba hardcodeada para facilitar el desarrollo
 * y las pruebas sin necesidad de tener la BD configurada.
 *
 * En la app real se usa RoomUserRepository (con Room).
 * Este fichero sirve como referencia de los usuarios disponibles para testear.
 *
 * Usuarios de prueba:
 *   ana.admin@club.es     / 1234  → ADMIN_DEPORTIVO
 *   pedro.entrenador@club.es / 1234 → ENTRENADOR
 *   laura.jugadora@club.es / 1234 → JUGADOR
 *   luis.arbitro@club.es  / 1234  → ARBITRO
 *   (etc.)
 */

package com.example.lab_jetpack_compose.data


import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository

object DataUserRepository : UserRepository {

    private val users = mutableListOf(
        User(
            id = 1,
            nombre = "Ana Pérez",
            email = "ana.admin@club.es",
            password = "1234",
            rol = "ADMIN_DEPORTIVO"
        ),
        User(
            id = 2,
            nombre = "Pedro Caselles",
            email = "pedro.entrenador@club.es",
            password = "1234",
            rol = "ENTRENADOR"
        ),
        User(
            id = 3,
            nombre = "Pepa Ferrández",
            email = "laura.jugadora@club.es",
            password = "1234",
            rol = "JUGADOR"
        ),
        User(
            id = 4,
            nombre = "Pablo Teruel",
            email = "luis.arbitro@club.es",
            password = "1234",
            rol = "ARBITRO"
        ),
        User(
            id = 5,
            nombre = "María Belmonte",
            email = "maria.jugadora@club.es",
            password = "1234",
            rol = "JUGADOR"
        ),
        User(
            id = 6,
            nombre = "Saúl Martínez",
            email = "saul.jugador@club.es",
            password = "1234",
            rol = "JUGADOR"
        ),
        User(
            id = 7,
            nombre = "Alba Rocío",
            email = "alba.jugadora@club.es",
            password = "1234",
            rol = "JUGADOR"
        ),
    )

    private fun getNewId(): Int {
        return (users.maxOfOrNull { it.id } ?: 0) + 1
    }

    // Crear usuario nuevo
    override suspend fun addUser(user: User): User {
        val newId = getNewId()
        val newUser = user.copy(id = newId)
        users.add(newUser)
        return newUser
    }

    // Obtener usuario por id
    override suspend fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    // Actualizar usuario
    override suspend fun updateUser(user: User): Boolean {
        val index = users.indexOfFirst { it.id == user.id }
        return if (index != -1) {
            users[index] = user
            true
        } else {
            false
        }
    }

    // Borrar usuario
    override suspend fun deleteUser(id: Int): Boolean {
        return users.removeIf { it.id == id }
    }

    // Obtener todos
    override suspend fun getAllUsers(): List<User> {
        return users
    }

    // Obtener por rol
    override suspend fun getUsersByRole(rol: String): List<User> =
        users.filter { it.rol == rol }

    override suspend fun getUserByEmail(email: String): User? {
        return users.find { it.email.equals(email, ignoreCase = true) }
    }

}
