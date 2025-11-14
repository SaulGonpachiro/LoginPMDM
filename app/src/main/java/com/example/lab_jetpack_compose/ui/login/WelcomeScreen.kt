package com.example.lab_jetpack_compose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.navigation.Routes
import com.example.lab_jetpack_compose.ui.login.components.CustomRed

@Composable
fun WelcomeScreen(navController: NavHostController, name: String) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.6f)
                .width(344.dp)
                .height(400.dp)
                .background(Color(0x34FFFFFF), RoundedCornerShape(16.dp))
        ) {}

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(344.dp)
                .height(400.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = "Bienvenido, $name!",
                color = CustomRed,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(225.dp)
                    .height(70.dp)
            ) {
                Text(
                    text = "Cerrar sesión",
                    color = CustomRed,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
