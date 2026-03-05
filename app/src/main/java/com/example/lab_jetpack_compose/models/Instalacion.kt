package com.example.lab_jetpack_compose.models

/** Entidad Room — tabla de instalaciones/pistas deportivas del centro.
 * Campos: nombre, tipo de pista, horario disponible y capacidad máxima.
 */

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "instalaciones")
data class Instalacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val tipo: String,
    val horario: String,
    val capacidad: Int
)

