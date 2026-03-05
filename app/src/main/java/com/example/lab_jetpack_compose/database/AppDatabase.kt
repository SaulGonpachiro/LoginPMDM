/**
 * AppDatabase
 *
 * Base de datos Room de la aplicación.
 *
 * Entidades registradas (cada una corresponde a una tabla):
 *   - User                    → tabla "usuarios"
 *   - Instalacion             → tabla de pistas deportivas
 *   - Partido                 → tabla de partidos
 *   - Reserva                 → tabla de reservas de pista
 *   - Team                    → tabla de equipos
 *   - EntrenamientoEquipo     → sesiones de entrenamiento creadas por entrenadores
 *   - InscripcionEntrenamiento → relación jugador ↔ sesión de entrenamiento
 *
 * Nota: fallbackToDestructiveMigration() elimina y recrea la BD si cambia el schema.
 * Útil en desarrollo, pero en producción se usarían migraciones reales.
 */

package com.example.lab_jetpack_compose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import com.example.lab_jetpack_compose.models.InscripcionEntrenamiento
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.models.Instalacion
import com.example.lab_jetpack_compose.models.Partido
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.models.Team



// @Database: le indica a Room qué tablas existen y qué versión tiene el esquema
// Cada vez que se añade o modifica una entidad hay que incrementar 'version'
// exportSchema=false: no guarda el historial de migraciones en un fichero JSON
@Database(
    entities = [User::class, Instalacion::class, Partido::class, Reserva::class,
        Team::class, EntrenamientoEquipo::class, InscripcionEntrenamiento::class],
    version = 7,
    exportSchema = false
)




// AppDatabase es abstracta: Room genera la implementación real en tiempo de compilación
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun instalacionDao(): InstalacionDao
    abstract fun partidoDao(): PartidoDao
    abstract fun reservaDao(): ReservaDao
    abstract fun teamDao(): TeamDao

    abstract fun entrenamientoEquipoDao(): com.example.lab_jetpack_compose.database.EntrenamientoEquipoDao
    abstract fun inscripcionEntrenamientoDao(): com.example.lab_jetpack_compose.database.InscripcionEntrenamientoDao



    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    // Para desarrollo (cambias tablas y no quieres migraciones)
                    // fallbackToDestructiveMigration: si la versión de la BD cambia y no hay migración definida,
                    // Room borra y recrea la BD. Útil en desarrollo, en producción se usarían migraciones.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
