package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import kotlinx.coroutines.flow.Flow

@Dao
interface EntrenamientoEquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EntrenamientoEquipo): Long

    @Update
    suspend fun update(item: EntrenamientoEquipo): Int

    @Query("DELETE FROM entrenamientos_equipo WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM entrenamientos_equipo ORDER BY fecha ASC, hora ASC, id ASC")
    fun observeAll(): Flow<List<EntrenamientoEquipo>>

    @Query("SELECT * FROM entrenamientos_equipo WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EntrenamientoEquipo?
}