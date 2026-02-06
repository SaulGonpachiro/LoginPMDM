package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    /** ✅ Precarga */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    /** ✅ Saber si está vacía */
    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun countUsers(): Int

    /** Flow (si lo usas en alguna pantalla) */
    @Query("SELECT * FROM usuarios ORDER BY nombre ASC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY nombre ASC")
    fun getByRole(rol: String): Flow<List<User>>

    /** Para tu interfaz (List) */
    @Query("SELECT * FROM usuarios ORDER BY nombre ASC")
    suspend fun getAllList(): List<User>

    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY nombre ASC")
    suspend fun getByRoleList(rol: String): List<User>

    /** ✅ Para borrar por id */
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): User?

    @Query("DELETE FROM usuarios WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    /** ✅ Para login */
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?

    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User): Int
}
