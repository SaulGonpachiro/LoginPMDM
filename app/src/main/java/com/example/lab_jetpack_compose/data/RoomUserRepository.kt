package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.UserDao
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository

class RoomUserRepository(private val userDao: UserDao) : UserRepository {

    override suspend fun getAllUsers(): List<User> = userDao.getAllList()

    override suspend fun getUsersByRole(rol: String): List<User> = userDao.getByRoleList(rol)

    override suspend fun getUserById(id: Int): User? = userDao.getById(id)

    override suspend fun getUserByEmail(email: String): User? = userDao.getByEmail(email)

    override suspend fun addUser(user: User): User {
        val newId = userDao.insert(user).toInt()
        return user.copy(id = newId)
    }

    override suspend fun updateUser(user: User): Boolean = userDao.update(user) > 0

    override suspend fun deleteUser(id: Int): Boolean = userDao.deleteById(id) > 0
}
