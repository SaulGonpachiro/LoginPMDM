package com.example.lab_jetpack_compose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.models.Instalacion
import com.example.lab_jetpack_compose.models.Partido
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.models.Team



@Database(
    entities = [User::class, Instalacion::class, Partido::class, Reserva::class, Team::class],
    version = 6,
    exportSchema = false
)




abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun instalacionDao(): InstalacionDao
    abstract fun partidoDao(): PartidoDao
    abstract fun reservaDao(): ReservaDao
    abstract fun teamDao(): TeamDao



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
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
