/**
 * AppContainer
 *
 * Contenedor de dependencias de la app (patrón manual de inyección de dependencias).
 * Se crea una sola vez en LabApp y se accede desde cualquier Composable via:
 *   val app = context.applicationContext as LabApp
 *   val repo = app.container.userRepository
 *
 * Expone todos los repositorios Room que usan las pantallas:
 *   - userRepository          → usuarios (login, registro, gestión)
 *   - instalacionRepository   → pistas / instalaciones deportivas
 *   - partidoRepository       → partidos entre equipos
 *   - reservaRepository       → reservas individuales de pistas
 *   - teamRepository          → equipos deportivos
 *   - entrenamientoEquipoRepository    → sesiones de entrenamiento de equipo
 *   - inscripcionEntrenamientoRepository → inscripciones de jugadores a sesiones
 */

package com.example.lab_jetpack_compose

import android.content.Context
import com.example.lab_jetpack_compose.data.RoomEntrenamientoEquipoRepository
import com.example.lab_jetpack_compose.data.RoomInscripcionEntrenamientoRepository
import com.example.lab_jetpack_compose.data.RoomUserRepository
import com.example.lab_jetpack_compose.database.AppDatabase
import com.example.lab_jetpack_compose.repository.UserRepository
import com.example.lab_jetpack_compose.data.RoomInstalacionRepository
import com.example.lab_jetpack_compose.repository.InstalacionRepository
import com.example.lab_jetpack_compose.data.RoomPartidoRepository
import com.example.lab_jetpack_compose.repository.PartidoRepository
import com.example.lab_jetpack_compose.data.RoomReservaRepository
import com.example.lab_jetpack_compose.repository.ReservaRepository
import com.example.lab_jetpack_compose.data.RoomTeamRepository
import com.example.lab_jetpack_compose.repository.TeamRepository



class AppContainer(context: Context) {
    // Creamos (o abrimos si ya existe) la base de datos Room
    // getDatabase usa el patrón Singleton: siempre devuelve la misma instancia
    private val db = AppDatabase.getDatabase(context)

    // Repositorio de usuarios: gestiona login, registro y CRUD de usuarios
    // Le pasamos el DAO correspondiente para que pueda hacer queries a Room
    val userRepository: UserRepository = RoomUserRepository(db.userDao())
    // Repositorio de instalaciones/pistas deportivas
    val instalacionRepository: InstalacionRepository = RoomInstalacionRepository(db.instalacionDao())

    // Repositorio de partidos entre equipos
    val partidoRepository: PartidoRepository = RoomPartidoRepository(db.partidoDao())
    // Repositorio de reservas individuales de pistas
    val reservaRepository: ReservaRepository = RoomReservaRepository(db.reservaDao())
    // Repositorio de equipos deportivos
    val teamRepository: TeamRepository = RoomTeamRepository(db.teamDao())

    // Repositorio de sesiones de entrenamiento (las crea el entrenador, los jugadores se apuntan)
    val entrenamientoEquipoRepository =
        RoomEntrenamientoEquipoRepository(db.entrenamientoEquipoDao())
    // Repositorio de inscripciones: relaciona cada jugador con las sesiones a las que está apuntado
    val inscripcionEntrenamientoRepository =
        RoomInscripcionEntrenamientoRepository(db.inscripcionEntrenamientoDao())
}
