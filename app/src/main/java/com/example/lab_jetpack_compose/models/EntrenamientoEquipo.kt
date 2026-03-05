package com.example.lab_jetpack_compose.models

/** Entidad Room — sesiones de entrenamiento creadas por un entrenador.
 * 'creadoPor' es el nombre del entrenador. Los jugadores pueden inscribirse via InscripcionEntrenamiento.
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrenamientos_equipo")
data class EntrenamientoEquipo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val equipoNombre: String,     // simple: nombre del equipo (no id)
    val tipoPista: String,        // tenis/pádel/etc (o tipo de pista)
    val fecha: String,            // "YYYY-MM-DD"
    val hora: String,             // "HH:MM"
    val capacidad: Int,           // plazas
    val creadoPor: String         // nombre del entrenador o admin (texto)
)