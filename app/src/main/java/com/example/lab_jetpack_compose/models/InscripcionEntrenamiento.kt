package com.example.lab_jetpack_compose.models

/** Entidad Room — relación jugador ↔ sesión de entrenamiento.
 * Cuando un jugador se apunta a un EntrenamientoEquipo se crea una fila aquí.
 * Al darse de baja se borra esa fila.
 */

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inscripciones_entrenamiento",
    indices = [Index(value = ["entrenamientoId", "jugadorNombre"], unique = true)]
)
data class InscripcionEntrenamiento(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entrenamientoId: Int,
    val jugadorNombre: String
)