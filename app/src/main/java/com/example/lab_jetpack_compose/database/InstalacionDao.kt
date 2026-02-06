package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.Instalacion
import kotlinx.coroutines.flow.Flow

@Dao
interface InstalacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(instalacion: Instalacion): Long

    @Update
    suspend fun update(instalacion: Instalacion): Int

    @Query("DELETE FROM instalaciones WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM instalaciones ORDER BY nombre ASC")
    fun observeAll(): Flow<List<Instalacion>>

    @Query("SELECT * FROM instalaciones ORDER BY nombre ASC")
    suspend fun getAll(): List<Instalacion>

    @Query("SELECT * FROM instalaciones WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Instalacion?
}
