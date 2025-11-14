package com.example.lab_jetpack_compose

import androidx.compose.runtime.Composable
import com.example.lab_jetpack_compose.data.LoginRepository
import com.example.lab_jetpack_compose.domain.LoginLogic
import com.example.lab_jetpack_compose.ui.login.components.LoginScreen

@Composable
fun LoginUi() {
    val logic = LoginLogic(LoginRepository())

    LoginScreen() { user, pass ->
        val success = logic.doLogin(user, pass)
        println(if (success) "✅ Login correcto" else "❌ Login incorrecto")
    }
}
