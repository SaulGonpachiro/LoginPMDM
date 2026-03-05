package com.example.lab_jetpack_compose.models

/** Entidad Room — tabla de partidos. Representa un partido entre dos equipos.
 * El admin puede crear, editar y borrar partidos desde GesPartidosScreen.
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidos")
data class Partido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val equipoLocal: String,
    val equipoVisitante: String,
    val fecha: String,   // ej: "2026-02-02" o "02/02/2026"
    val hora: String     // ej: "18:30"
)
