package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.EntrenamientoEquipoDao
import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import com.example.lab_jetpack_compose.repository.EntrenamientoEquipoRepository
import kotlinx.coroutines.flow.Flow

class RoomEntrenamientoEquipoRepository(
    private val dao: EntrenamientoEquipoDao
) : EntrenamientoEquipoRepository {

    override fun observeAll(): Flow<List<EntrenamientoEquipo>> = dao.observeAll()

    override suspend fun add(item: EntrenamientoEquipo): Long = dao.insert(item)

    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}