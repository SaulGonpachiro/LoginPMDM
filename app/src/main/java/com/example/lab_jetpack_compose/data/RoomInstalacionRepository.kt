package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.InstalacionDao
import com.example.lab_jetpack_compose.models.Instalacion
import com.example.lab_jetpack_compose.repository.InstalacionRepository
import kotlinx.coroutines.flow.Flow

class RoomInstalacionRepository(
    private val dao: InstalacionDao
) : InstalacionRepository {

    override fun observeAll(): Flow<List<Instalacion>> = dao.observeAll()

    override suspend fun getAll(): List<Instalacion> = dao.getAll()

    override suspend fun getById(id: Int): Instalacion? = dao.getById(id)

    override suspend fun add(inst: Instalacion): Instalacion {
        val newId = dao.insert(inst).toInt()
        return inst.copy(id = newId)
    }

    override suspend fun update(inst: Instalacion): Boolean = dao.update(inst) > 0

    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}
