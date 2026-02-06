package com.example.lab_jetpack_compose.models

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
