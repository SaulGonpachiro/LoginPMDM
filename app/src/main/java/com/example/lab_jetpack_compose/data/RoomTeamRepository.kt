package com.example.lab_jetpack_compose.data

import com.example.lab_jetpack_compose.database.TeamDao
import com.example.lab_jetpack_compose.models.Team
import com.example.lab_jetpack_compose.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class RoomTeamRepository(
    private val dao: TeamDao
) : TeamRepository {

    override fun observeAll(): Flow<List<Team>> = dao.observeAll()
    override suspend fun getAll(): List<Team> = dao.getAll()
    override suspend fun getById(id: Int): Team? = dao.getById(id)

    override suspend fun add(t: Team): Team {
        val newId = dao.insert(t).toInt()
        return t.copy(id = newId)
    }

    override suspend fun update(t: Team): Boolean = dao.update(t) > 0
    override suspend fun delete(id: Int): Boolean = dao.deleteById(id) > 0
}
