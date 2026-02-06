package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.Partido
import kotlinx.coroutines.flow.Flow

interface PartidoRepository {
    fun observeAll(): Flow<List<Partido>>
    suspend fun getAll(): List<Partido>
    suspend fun getById(id: Int): Partido?
    suspend fun add(p: Partido): Partido
    suspend fun update(p: Partido): Boolean
    suspend fun delete(id: Int): Boolean
}
