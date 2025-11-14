package com.example.lab_jetpack_compose.models



data class User (
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String
)