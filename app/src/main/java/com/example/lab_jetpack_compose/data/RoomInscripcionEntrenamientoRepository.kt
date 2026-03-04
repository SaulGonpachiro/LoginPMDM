package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.InscripcionEntrenamientoDao
import com.example.lab_jetpack_compose.models.InscripcionEntrenamiento
import com.example.lab_jetpack_compose.repository.InscripcionEntrenamientoRepository
import kotlinx.coroutines.flow.Flow

class RoomInscripcionEntrenamientoRepository(
    private val dao: InscripcionEntrenamientoDao
) : InscripcionEntrenamientoRepository {

    override fun observeAll(): Flow<List<InscripcionEntrenamiento>> = dao.observeAll()

    override fun observeByJugador(nombre: String): Flow<List<InscripcionEntrenamiento>> =
        dao.observeByJugador(nombre)

    override suspend fun apuntar(entrenamientoId: Int, jugadorNombre: String): Boolean {
        val id = dao.insert(
            InscripcionEntrenamiento(entrenamientoId = entrenamientoId, jugadorNombre = jugadorNombre)
        )
        return id != -1L
    }

    override suspend fun baja(entrenamientoId: Int, jugadorNombre: String): Boolean =
        dao.deleteBy(entrenamientoId, jugadorNombre) > 0

    override suspend fun isInscrito(entrenamientoId: Int, jugadorNombre: String): Boolean =
        dao.isInscrito(entrenamientoId, jugadorNombre)

    override suspend fun count(entrenamientoId: Int): Int =
        dao.countByEntrenamiento(entrenamientoId)
}