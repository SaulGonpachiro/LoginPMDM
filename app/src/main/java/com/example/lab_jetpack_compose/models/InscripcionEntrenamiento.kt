package com.example.lab_jetpack_compose.models

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