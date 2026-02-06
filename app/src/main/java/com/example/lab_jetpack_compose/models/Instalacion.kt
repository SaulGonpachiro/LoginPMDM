package com.example.lab_jetpack_compose.models

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

