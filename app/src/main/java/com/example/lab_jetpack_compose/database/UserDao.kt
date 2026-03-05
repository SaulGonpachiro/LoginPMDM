package com.example.lab_jetpack_compose.database

import androidx.room.*
import com.example.lab_jetpack_compose.models.User
import kotlinx.coroutines.flow.Flow

// @Dao: Room genera automáticamente la implementación de esta interfaz en tiempo de compilación
// Cada función anotada se convierte en una query SQL real
@Dao
interface UserDao {

    // Inserta un usuario. Si ya existe uno con el mismo email (índice único), lo reemplaza.
    // Devuelve el id generado automáticamente por Room (Long)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    /** ✅ Precarga */
    // Inserta varios usuarios de golpe — se usa al precargar el users.json al arrancar
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    /** ✅ Saber si está vacía */
    // Cuenta cuántos usuarios hay en la tabla — se usa para saber si la BD está vacía antes del seed
    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun countUsers(): Int

    // Flow reactivo de todos los usuarios ordenados por nombre
    // La UI se actualiza automáticamente cuando cambia la tabla
    @Query("SELECT * FROM usuarios ORDER BY nombre ASC")
    fun getAll(): Flow<List<User>>

    // Flow de usuarios filtrados por rol — ej: solo JUGADOR o solo ENTRENADOR
    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY nombre ASC")
    fun getByRole(rol: String): Flow<List<User>>

    // Versión suspend (no Flow) — devuelve la lista una sola vez — usada en GesUserViewModel
    @Query("SELECT * FROM usuarios ORDER BY nombre ASC")
    suspend fun getAllList(): List<User>

    // Lista de usuarios por rol — versión suspend
    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY nombre ASC")
    suspend fun getByRoleList(rol: String): List<User>

    // Busca un usuario por su id — usado en HomeScreen para cargar el perfil del usuario logueado
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): User?

    // Borra el usuario con ese id. Devuelve el número de filas afectadas (1 si se borró, 0 si no existía)
    @Query("DELETE FROM usuarios WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    // Busca usuario por email — es el punto de entrada del login
    // Devuelve null si el email no existe en la BD
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?

    // Actualiza todos los campos del usuario que tenga el mismo id
    // Devuelve el número de filas actualizadas
    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User): Int
}
