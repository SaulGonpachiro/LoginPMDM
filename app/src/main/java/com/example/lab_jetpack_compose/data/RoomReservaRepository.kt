package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.ReservaDao
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.repository.ReservaRepository
import kotlinx.coroutines.flow.Flow

class RoomReservaRepository(
    private val dao: ReservaDao
) : ReservaRepository {

    override fun observeAll(): Flow<List<Reserva>> = dao.observeAll()
    override suspend fun getAll(): List<Reserva> = dao.getAll()
    override suspend fun getById(id: Int): Reserva? = dao.getById(id)

    override suspend fun add(r: Reserva): Reserva {
        val newId = dao.insert(r).toInt()
        return r.copy(id = newId)
    }

    override suspend fun update(r: Reserva): Boolean = dao.update(r) > 0
    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}
