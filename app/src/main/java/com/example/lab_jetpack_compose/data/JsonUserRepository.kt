package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


class JsonUserRepository: UserRepository {

    private val jsonFile = File("user.json")

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val users = mutableListOf<User>()

    private var nextId: Int = 1

    init {
        loadFromFile()
    }

    private fun loadFromFile(){
        if (!jsonFile.exists()){
            users.clear()
            nextId = 1
        }
        val text = jsonFile.readText()
        if (text.isBlank()){
            users.clear()
            nextId = 1
            return
        }

        val loadedUsers: List<User> = json.decodeFromString(text)
        users.clear()
        users.addAll(loadedUsers)


        nextId = (users.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun saveToFile(){
        val text = json.encodeToString(users)
        jsonFile.writeText(text)
    }

    private fun getNewId(): Int {
        return (users.maxOfOrNull { it.id } ?: 0) + 1
    }

    override suspend fun getAllUsers(): List<User> {
        return users.toList()
    }

    override suspend fun getUsersByRole(rol: String): List<User> {
        return users.filter { it.rol == rol}
    }

    override suspend fun getUserById(id: Int): User? {
        return users.find { it.id == id}
    }

    override suspend fun addUser(user: User): User {
        nextId = getNewId()
        val newUser = user.copy(id = nextId)
        users.add(newUser)
        saveToFile()
        return newUser
    }

    override suspend fun updateUser(user: User): Boolean {
        val index = users.indexOfFirst { it.id == user.id }
        if (index == 1) return false

        users[index] = user
        saveToFile()
        return true
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val removed = users.removeIf { it.id == id }
        if (removed) {
            saveToFile()
        }
        return removed
    }
}

suspend fun addUser(user: User): User {
    TODO("Not yet implemented")
}

suspend fun updateUser(user: User): Boolean {
    TODO("Not yet implemented")
}