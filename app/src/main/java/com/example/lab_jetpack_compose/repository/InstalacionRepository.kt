package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.Instalacion
import kotlinx.coroutines.flow.Flow

interface InstalacionRepository {
    fun observeAll(): Flow<List<Instalacion>>
    suspend fun getAll(): List<Instalacion>
    suspend fun getById(id: Int): Instalacion?
    suspend fun add(inst: Instalacion): Instalacion
    suspend fun update(inst: Instalacion): Boolean
    suspend fun delete(id: Int): Boolean
}
