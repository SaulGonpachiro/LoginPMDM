package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.PartidoDao
import com.example.lab_jetpack_compose.models.Partido
import com.example.lab_jetpack_compose.repository.PartidoRepository
import kotlinx.coroutines.flow.Flow

class RoomPartidoRepository(
    private val dao: PartidoDao
) : PartidoRepository {

    override fun observeAll(): Flow<List<Partido>> = dao.observeAll()
    override suspend fun getAll(): List<Partido> = dao.getAll()
    override suspend fun getById(id: Int): Partido? = dao.getById(id)

    override suspend fun add(p: Partido): Partido {
        val newId = dao.insert(p).toInt()
        return p.copy(id = newId)
    }

    override suspend fun update(p: Partido): Boolean = dao.update(p) > 0
    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}
