package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.ReservaDao
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.repository.ReservaRepository
import kotlinx.coroutines.flow.Flow

// Implementación Room de ReservaRepository
// Se usa tanto desde HomeScreen (reservas del jugador) como desde GesReservaScreen (admin ve todas)
class RoomReservaRepository(
    private val dao: ReservaDao
) : ReservaRepository {

    // Devuelve un Flow reactivo — cada vez que cambia la tabla 'reservas', la UI se actualiza sola
    override fun observeAll(): Flow<List<Reserva>> = dao.observeAll()
    override suspend fun getAll(): List<Reserva> = dao.getAll()
    override suspend fun getById(id: Int): Reserva? = dao.getById(id)

    // Inserta la reserva y devuelve el objeto con el id generado por Room
    override suspend fun add(r: Reserva): Reserva {
        val newId = dao.insert(r).toInt()
        return r.copy(id = newId)
    }

    override suspend fun update(r: Reserva): Boolean = dao.update(r) > 0
    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}
