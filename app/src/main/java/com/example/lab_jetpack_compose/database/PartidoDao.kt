package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.Partido
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(partido: Partido): Long

    @Update
    suspend fun update(partido: Partido): Int

    @Query("DELETE FROM partidos WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM partidos ORDER BY fecha ASC, hora ASC")
    fun observeAll(): Flow<List<Partido>>

    @Query("SELECT * FROM partidos ORDER BY fecha ASC, hora ASC")
    suspend fun getAll(): List<Partido>

    @Query("SELECT * FROM partidos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Partido?
}
