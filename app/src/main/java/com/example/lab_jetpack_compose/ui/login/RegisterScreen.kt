package com.example.lab_jetpack_compose.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.navigation.Routes
import com.example.lab_jetpack_compose.ui.login.components.CustomRed

@Composable
fun RegisterScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPass by remember { mutableStateOf("") }

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
                .height(539.dp)
                .background(Color(0x34FFFFFF), RoundedCornerShape(16.dp))
        ) {
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(344.dp)
                .height(539.dp)
                .padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                "REGISTRO",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = CustomRed
            )

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = CustomRed, fontWeight = FontWeight.Bold) },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = CustomRed,
                    focusedBorderColor = CustomRed,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .width(246.dp)
                    .background(Color(0x7CFFFFFF))
            )

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text("Contraseña", color = CustomRed, fontWeight = FontWeight.Bold)
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = CustomRed,
                    focusedBorderColor = CustomRed,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .width(246.dp)
                    .background(Color(0x7CFFFFFF))
            )

            // Repetir contraseña
            OutlinedTextField(
                value = repeatPass,
                onValueChange = { repeatPass = it },
                label = {
                    Text("Repetir contraseña", color = CustomRed, fontWeight = FontWeight.Bold)
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = CustomRed,
                    focusedBorderColor = CustomRed,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .width(246.dp)
                    .background(Color(0x7CFFFFFF))
            )

            // Botón crear cuenta
            Button(
                onClick = {
                    // Aquí podrías validar y guardar el usuario si quisieras
                    navController.navigate(Routes.Login.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(225.dp)
                    .height(70.dp)
            ) {
                Text(
                    "Crear cuenta",
                    fontSize = 20.sp,
                    color = CustomRed,
                    fontWeight = FontWeight.Bold
                )
            }

            // Volver atrás
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(225.dp)
                    .height(60.dp)
            ) {
                Text(
                    "Volver",
                    fontSize = 18.sp,
                    color = CustomRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
