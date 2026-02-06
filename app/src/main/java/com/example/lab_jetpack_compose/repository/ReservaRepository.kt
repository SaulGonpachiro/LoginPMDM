package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.Reserva
import kotlinx.coroutines.flow.Flow

interface ReservaRepository {
    fun observeAll(): Flow<List<Reserva>>
    suspend fun getAll(): List<Reserva>
    suspend fun getById(id: Int): Reserva?
    suspend fun add(r: Reserva): Reserva
    suspend fun update(r: Reserva): Boolean
    suspend fun delete(id: Int): Boolean
}
