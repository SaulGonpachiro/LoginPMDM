package com.example.lab_jetpack_compose.domain

import com.example.lab_jetpack_compose.data.LoginRepository
import com.example.lab_jetpack_compose.models.User


/*class LoginLogic(private val repository: LoginRepository = LoginRepository()) {
    fun doLogin(username: String, password: String): Boolean {
        return repository.login(username, password)
    }
}
 */

class LogicLogin {
    fun comprobarLogin(email: String, password: String): User {
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Los campos no pueden estar vacíos.")
        }

        val user = LoginRepository.obtenerUsuarios()
            .find { it.email == email && it.password == password }
            ?: throw IllegalArgumentException("Email o contraseña incorrectos.")

        return user
    }
}