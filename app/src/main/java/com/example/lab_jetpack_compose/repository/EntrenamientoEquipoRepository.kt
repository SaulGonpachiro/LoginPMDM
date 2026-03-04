package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import kotlinx.coroutines.flow.Flow

interface EntrenamientoEquipoRepository {
    fun observeAll(): Flow<List<EntrenamientoEquipo>>
    suspend fun add(item: EntrenamientoEquipo): Long
    suspend fun delete(id: Int): Boolean
}