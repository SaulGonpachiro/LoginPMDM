package com.example.lab_jetpack_compose.models

/** Entidad Room — tabla 'reservas'. Representa una reserva individual de una pista.
 * 'nombre' es el nombre del usuario que reservó (se usa para filtrar las suyas).
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val tipoPista: String,
    val fecha: String,
    val hora: String,
    val capacidad: Int
)
