package com.example.lab_jetpack_compose

import android.app.Application
import android.util.Log
import com.example.lab_jetpack_compose.database.AppDatabase
import com.example.lab_jetpack_compose.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LabApp : Application() {

    lateinit var container: AppContainer
        private set

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)

        appScope.launch {
            seedUsersIfEmpty()
        }
    }

    private suspend fun seedUsersIfEmpty() {
        val dao = AppDatabase.getDatabase(this).userDao()

        val count = dao.countUsers()
        Log.d("SEED", "countUsers = $count")
        if (count > 0) return

        val jsonText = assets.open("users.json")
            .bufferedReader()
            .use { it.readText() }

        val users: List<User> = Json { ignoreUnknownKeys = true }
            .decodeFromString(jsonText)

        dao.insertAll(users)
        Log.d("SEED", "Inserted users: ${users.size}")
    }
}
