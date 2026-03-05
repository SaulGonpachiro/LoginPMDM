package com.example.lab_jetpack_compose.database

/** DAO de reservas. Operaciones CRUD + Flow de todas las reservas para observación reactiva. */

import androidx.room.*
import com.example.lab_jetpack_compose.models.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {


    // Inserta una reserva nueva. Devuelve el id generado por Room.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reserva: Reserva): Long

    // Actualiza una reserva existente (busca por id)
    @Update
    suspend fun update(reserva: Reserva): Int

    // Borra la reserva con ese id — se llama al cancelar una reserva
    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    // Flow reactivo de todas las reservas ordenadas por fecha y hora
    // HomeScreen y GesReservaScreen se suscriben a este Flow — se actualiza solo al insertar/borrar
    @Query("SELECT * FROM reservas ORDER BY fecha ASC, hora ASC")
    fun observeAll(): Flow<List<Reserva>>

    // Versión suspend (una sola lectura) — usada en GesReservaViewModel
    @Query("SELECT * FROM reservas ORDER BY fecha ASC, hora ASC")
    suspend fun getAll(): List<Reserva>

    @Query("SELECT * FROM reservas WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Reserva?
}
