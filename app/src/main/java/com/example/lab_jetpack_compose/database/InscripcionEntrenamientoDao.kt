package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.InscripcionEntrenamiento
import kotlinx.coroutines.flow.Flow

@Dao
interface InscripcionEntrenamientoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: InscripcionEntrenamiento): Long

    @Query("DELETE FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId AND jugadorNombre = :jugadorNombre")
    suspend fun deleteBy(entrenamientoId: Int, jugadorNombre: String): Int

    @Query("SELECT * FROM inscripciones_entrenamiento ORDER BY id DESC")
    fun observeAll(): Flow<List<InscripcionEntrenamiento>>

    @Query("SELECT * FROM inscripciones_entrenamiento WHERE jugadorNombre = :jugadorNombre ORDER BY id DESC")
    fun observeByJugador(jugadorNombre: String): Flow<List<InscripcionEntrenamiento>>

    @Query("SELECT COUNT(*) FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId")
    suspend fun countByEntrenamiento(entrenamientoId: Int): Int

    @Query("SELECT EXISTS(SELECT 1 FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId AND jugadorNombre = :jugadorNombre)")
    suspend fun isInscrito(entrenamientoId: Int, jugadorNombre: String): Boolean
}