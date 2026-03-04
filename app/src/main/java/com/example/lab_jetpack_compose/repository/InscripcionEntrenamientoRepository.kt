package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.InscripcionEntrenamiento
import kotlinx.coroutines.flow.Flow

interface InscripcionEntrenamientoRepository {
    fun observeAll(): Flow<List<InscripcionEntrenamiento>>
    fun observeByJugador(nombre: String): Flow<List<InscripcionEntrenamiento>>
    suspend fun apuntar(entrenamientoId: Int, jugadorNombre: String): Boolean
    suspend fun baja(entrenamientoId: Int, jugadorNombre: String): Boolean
    suspend fun isInscrito(entrenamientoId: Int, jugadorNombre: String): Boolean
    suspend fun count(entrenamientoId: Int): Int
}