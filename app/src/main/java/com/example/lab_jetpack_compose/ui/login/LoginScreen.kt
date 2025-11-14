package com.example.lab_jetpack_compose.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.domain.LogicLogin
import kotlinx.coroutines.delay

val CustomRed = Color(0xFFB40900)
val SemiTransparentWhite = Color(0x34FFFFFF)

@Composable
fun LoginScreen(navController: NavHostController) {
    //val logic = remember { LogicLogin()
    //var email by remember {mutableStateOf("") }
    //var password by remember {mutableStateOf("") }
    //var errorMessage by remember {mutableStateOfString("") }



    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // 1️⃣ Fondo completo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2️⃣ Logo (ahora garantizado que se muestra encima del fondo)
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .width(285.dp)
                .height(154.dp)
        )

        // 3️⃣ Caja semi-transparente central
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.6f)
                .width(344.dp)
                .height(539.dp)
                .background(
                    color = SemiTransparentWhite,
                    shape = RoundedCornerShape(16.dp)
                )
        ) { }

        // 4️⃣ Contenido principal (botones, campos de texto, etc.)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(344.dp)
                .height(539.dp)
                .padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            // Botón Login
            Button(
                onClick = {
                    val user = logic.comprobarLogin(email, password)
                    navController.navigate("home/${user.nombre}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(225.dp)
                    .height(98.dp)
            ) {
                Text(
                    text = "LOGIN",
                    color = CustomRed,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            // Campo usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre", color = CustomRed, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = CustomRed,
                    focusedBorderColor = CustomRed,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .width(246.dp)
                    .background(Color(0x7CFFFFFF))
            )

            // Campo contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = CustomRed, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = CustomRed,
                    focusedBorderColor = CustomRed,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier
                    .width(249.dp)
                    .background(Color(0x7CFFFFFF))
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Botón Google
            LoginButton(
                onClick = { mensaje = "Aún no hace una mierda" },
                text = "Conectar con Google",
                iconResId = R.drawable.google,
                iconSize = 40.dp
            )

            // Botón Apple
            LoginButton(
                onClick = { mensaje = "Aún no hace una mierda" },
                text = "Conectar con AppleID",
                iconResId = R.drawable.apple,
                iconSize = 60.dp
            )

            // Mensaje temporal
            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )

                LaunchedEffect(mensaje) {
                    delay(3000)
                    mensaje = ""
                }
            }
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit, text: String, iconResId: Int, iconSize: Dp) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier
            .width(304.dp)
            .height(70.dp)
            .padding(vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = CustomRed,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
