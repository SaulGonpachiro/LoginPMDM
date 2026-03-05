package com.example.lab_jetpack_compose.database

/** DAO de sesiones de entrenamiento de equipo. CRUD + Flow reactivo. */

import androidx.room.*
import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import kotlinx.coroutines.flow.Flow

@Dao
interface EntrenamientoEquipoDao {

    // Crea una sesión de entrenamiento de equipo (la crea el entrenador desde HomeScreen)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EntrenamientoEquipo): Long

    @Update
    suspend fun update(item: EntrenamientoEquipo): Int

    // Borra la sesión — solo el admin desde GesPartidosScreen puede borrar directamente
    @Query("DELETE FROM entrenamientos_equipo WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    // Flow reactivo de todas las sesiones — HomeScreen (pestaña Equipo) se suscribe aquí
    @Query("SELECT * FROM entrenamientos_equipo ORDER BY fecha ASC, hora ASC, id ASC")
    fun observeAll(): Flow<List<EntrenamientoEquipo>>

    @Query("SELECT * FROM entrenamientos_equipo WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EntrenamientoEquipo?
}