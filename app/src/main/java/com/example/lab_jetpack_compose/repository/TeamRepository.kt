package com.example.lab_jetpack_compose.repository

import com.example.lab_jetpack_compose.models.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun observeAll(): Flow<List<Team>>
    suspend fun getAll(): List<Team>
    suspend fun getById(id: Int): Team?
    suspend fun add(t: Team): Team
    suspend fun update(t: Team): Boolean
    suspend fun delete(id: Int): Boolean
}
