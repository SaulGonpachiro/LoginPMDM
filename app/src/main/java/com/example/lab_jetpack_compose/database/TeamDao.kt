package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team): Long

    @Update
    suspend fun update(team: Team): Int

    @Query("DELETE FROM teams WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM teams ORDER BY nombre ASC")
    fun observeAll(): Flow<List<Team>>

    @Query("SELECT * FROM teams ORDER BY nombre ASC")
    suspend fun getAll(): List<Team>

    @Query("SELECT * FROM teams WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Team?
}
