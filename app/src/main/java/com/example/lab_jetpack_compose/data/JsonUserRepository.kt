package com.example.lab_jetpack_compose.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.repository.UserRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonUserRepository(private val context: Context) : UserRepository {
    private  val TAG="FicheroJson"
    private val jsonFile = File(context.filesDir, "users.json")//FICHERO DINÁMICO

    val jsonString = context.assets.open("users.json")//FICHERO DE INICIO CARGADO DESDE ASSETS
        .bufferedReader()
        .use { it.readText() }

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val users = mutableListOf<User>()
    private var nextId: Int = 1

    init {
        Log.i(TAG, "Entra en INIT")
        loadFile()
        //if (jsonFile.exists())jsonFile.delete()
    }

    private fun loadFile(){
        Log.i(TAG, "Carga Fichero")
        //Intentar leer del fichero interno
        if (jsonFile.exists()){
            Log.i(TAG, "Encontrado Fichero")
            val text = jsonFile.readText()
            if (text.isNotBlank()){
                val loadedUser: List<User> = json.decodeFromString(text)
                users.clear()
                users.addAll(loadedUser)
                nextId = (users.maxOfOrNull { it.id } ?: 0) + 1
            }
        }
        else{
            Log.i(TAG, "Carga desde Assets")
            loadFromAssets()
            saveToFile()
        }
    }

    private fun loadFromAssets() {
        if (jsonString.isEmpty()) {// Si esta la variable vacia. Limpiamos e inicializamos el id a 1
            users.clear()
            nextId = 1
            return
        }
        if (jsonString.isBlank()){
            users.clear()
            nextId = 1
            return
        }
        val loadUser: List<User> = json.decodeFromString(jsonString)
        users.clear()
        users.addAll(loadUser)

        nextId = (users.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun saveToFile() {
        val text = json.encodeToString(users)
        jsonFile.writeText(text)
    }

    private fun getNewId(): Int =
        (users.maxOfOrNull { it.id } ?: 0) + 1

    override suspend fun getAllUsers(): List<User> = users.toList()

    override suspend fun getUsersByRole(rol: String): List<User> =
        users.filter { it.rol == rol }

    override suspend fun getUserById(id: Int): User? =
        users.find { it.id == id }

    override suspend fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(user: User): User {
        val newUser = user.copy(id = getNewId())
        users.add(newUser)
        saveToFile()
        return newUser
    }

    override suspend fun updateUser(user: User): Boolean {
        val index = users.indexOfFirst { it.id == user.id }
        if (index == -1) return false   // ojo: era -1, no 1

        users[index] = user
        saveToFile()
        return true
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val removed = users.removeIf { it.id == id }
        if (removed) saveToFile()
        return removed
    }
}
