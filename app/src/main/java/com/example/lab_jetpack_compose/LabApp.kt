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

    // AppContainer expone todos los repositorios Room de la app
    // 'lateinit' porque se inicializa en onCreate(), no en el constructor
    lateinit var container: AppContainer
        private set

    // Scope de corrutinas propio de la app, en el hilo de IO (operaciones de disco/BD)
    // SupervisorJob: si una corrutina falla, las demás no se cancelan
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        // Creamos el AppContainer: construye la BD Room y todos los repositorios
        container = AppContainer(this)

        // Lanzamos en background la precarga inicial de usuarios
        appScope.launch {
            seedUsersIfEmpty()
        }
    }

    // Precarga usuarios desde assets/users.json en Room, pero SOLO si la tabla está vacía
    // Así los usuarios de prueba están disponibles desde el primer arranque
    private suspend fun seedUsersIfEmpty() {
        val dao = AppDatabase.getDatabase(this).userDao()

        // Contamos los usuarios actuales en Room: SELECT COUNT(*) FROM usuarios
        val count = dao.countUsers()
        Log.d("SEED", "countUsers = $count")
        // Si ya hay datos, no insertamos nada (evita duplicados en reinicios)
        if (count > 0) return

        val jsonText = assets.open("users.json")
            .bufferedReader()
            .use { it.readText() }

        // Deserializamos el JSON a lista de objetos User usando kotlinx.serialization
        // ignoreUnknownKeys=true: si el JSON tiene campos extra no declarados en User, los ignora
        val users: List<User> = Json { ignoreUnknownKeys = true }
            .decodeFromString(jsonText)

        // Insertamos todos los usuarios de una vez en Room
        // @Insert(onConflict=REPLACE): si ya existe el email, lo sobreescribe (por seguridad)
        dao.insertAll(users)
        Log.d("SEED", "Inserted users: ${users.size}")
    }
}
