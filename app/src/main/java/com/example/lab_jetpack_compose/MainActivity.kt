package com.example.lab_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lab_jetpack_compose.navigation.AppNavigation
import com.example.lab_jetpack_compose.ui.theme.Lab_jetpack_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab_jetpack_composeTheme {
                AppNavigation()
            }
        }
    }
}
