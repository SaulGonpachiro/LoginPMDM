package com.example.lab_jetpack_compose.database

/** DAO de inscripciones. Permite apuntar/dar de baja jugadores de sesiones de entrenamiento.
 * Incluye queries para obtener inscripciones por jugador (nombre) y por sesión (entrenamientoId). */

import androidx.room.*
import com.example.lab_jetpack_compose.models.InscripcionEntrenamiento
import kotlinx.coroutines.flow.Flow

@Dao
interface InscripcionEntrenamientoDao {

    // Apunta al jugador a una sesión. IGNORE: si ya está apuntado, no hace nada (evita duplicados)
    // Devuelve -1L si ya existía la inscripción (por el IGNORE)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: InscripcionEntrenamiento): Long

    // Da de baja al jugador de una sesión concreta — borra la fila con esa combinación
    @Query("DELETE FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId AND jugadorNombre = :jugadorNombre")
    suspend fun deleteBy(entrenamientoId: Int, jugadorNombre: String): Int

    @Query("SELECT * FROM inscripciones_entrenamiento ORDER BY id DESC")
    fun observeAll(): Flow<List<InscripcionEntrenamiento>>

    // Flow de inscripciones de un jugador concreto — HomeScreen lo usa para saber a qué sesiones está apuntado
    @Query("SELECT * FROM inscripciones_entrenamiento WHERE jugadorNombre = :jugadorNombre ORDER BY id DESC")
    fun observeByJugador(jugadorNombre: String): Flow<List<InscripcionEntrenamiento>>

    @Query("SELECT COUNT(*) FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId")
    suspend fun countByEntrenamiento(entrenamientoId: Int): Int

    // Comprueba si el jugador ya está apuntado a una sesión — devuelve true/false
    @Query("SELECT EXISTS(SELECT 1 FROM inscripciones_entrenamiento WHERE entrenamientoId = :entrenamientoId AND jugadorNombre = :jugadorNombre)")
    suspend fun isInscrito(entrenamientoId: Int, jugadorNombre: String): Boolean
}