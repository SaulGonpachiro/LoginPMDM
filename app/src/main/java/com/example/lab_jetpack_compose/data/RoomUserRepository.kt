package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.UserDao
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository

// RoomUserRepository: implementación real de UserRepository usando Room
// Recibe el UserDao que Room generó para hacer las queries
// Los ViewModels y pantallas solo conocen la interfaz UserRepository, no esta clase concreta
class RoomUserRepository(private val userDao: UserDao) : UserRepository {

    // Delega directamente al DAO — SELECT * FROM usuarios ORDER BY nombre ASC
    override suspend fun getAllUsers(): List<User> = userDao.getAllList()

    override suspend fun getUsersByRole(rol: String): List<User> = userDao.getByRoleList(rol)

    override suspend fun getUserById(id: Int): User? = userDao.getById(id)

    // Punto clave del login — busca el usuario por email para verificar credenciales
    override suspend fun getUserByEmail(email: String): User? = userDao.getByEmail(email)

    // Inserta el usuario en Room y devuelve el objeto con el id real que Room le asignó
    // user.copy(id=newId) crea una copia del objeto con el id actualizado sin mutar el original
    override suspend fun addUser(user: User): User {
        val newId = userDao.insert(user).toInt()
        return user.copy(id = newId)
    }

    // Actualiza el usuario. Devuelve true si se actualizó al menos 1 fila
    override suspend fun updateUser(user: User): Boolean = userDao.update(user) > 0

    // Borra por id. Devuelve true si se borró al menos 1 fila
    override suspend fun deleteUser(id: Int): Boolean = userDao.deleteById(id) > 0
}
