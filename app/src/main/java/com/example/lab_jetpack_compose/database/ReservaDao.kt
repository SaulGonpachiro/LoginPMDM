package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reserva: Reserva): Long

    @Update
    suspend fun update(reserva: Reserva): Int

    @Query("DELETE FROM reservas WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM reservas ORDER BY fecha ASC, hora ASC")
    fun observeAll(): Flow<List<Reserva>>

    @Query("SELECT * FROM reservas ORDER BY fecha ASC, hora ASC")
    suspend fun getAll(): List<Reserva>

    @Query("SELECT * FROM reservas WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Reserva?
}
