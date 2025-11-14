package com.example.lab_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lab_jetpack_compose.navigation.Navigation
import com.example.lab_jetpack_compose.ui.theme.Lab_jetpack_composeTheme // ✅ usa tu tema real

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Envuelve todo con tu tema de Compose
            Lab_jetpack_composeTheme {
                // Carga la interfaz de login
                //LoginUi()
                Navigation()
            }
        }
    }
}


