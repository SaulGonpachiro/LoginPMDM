package com.example.lab_jetpack_compose.models

/** Entidad Room — tabla de equipos deportivos.
 * Cada equipo tiene nombre, deporte (FUTBOL, PADEL...), categoría (Infantil, Senior...) y capacidad máxima.
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val deporte: String,
    val categoria: String,
    val capacidad: Int
)
