package com.example.lab_jetpack_compose.models

import kotlinx.serialization.Serializable


@Serializable
data class User (
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String
)