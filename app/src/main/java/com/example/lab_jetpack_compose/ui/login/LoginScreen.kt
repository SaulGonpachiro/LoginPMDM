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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.LabApp
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.navigation.Routes
import kotlinx.coroutines.launch

val CustomRed = Color(0xFFB40900)
val PrimaryRed = Color(0xFF8F0700)
val SemiTransparentWhite = Color(0x34FFFFFF)

@Composable
// LoginScreen: pantalla de inicio de sesión y registro rápido
// navController: permite navegar a otras pantallas tras el login
fun LoginScreen(navController: NavHostController) {

    // Estado del campo email — rememberSaveable conserva el valor si el sistema mata la UI
    var email by rememberSaveable { mutableStateOf("") }
    // Estado del campo contraseña
    var password by rememberSaveable { mutableStateOf("") }
    // Mensaje de error visible debajo del formulario
    var mensaje by remember { mutableStateOf("") }

    // ✅ Repo de Room
    val context = LocalContext.current
    val app = context.applicationContext as LabApp
    // Obtenemos el repositorio de usuarios desde AppContainer
    // AppContainer vive en LabApp (la clase Application) y se crea una sola vez al arrancar la app
    val userRepository = app.container.userRepository
    // scope nos permite lanzar corrutinas desde un Composable
    // Las operaciones de Room NUNCA se pueden hacer en el hilo principal — necesitan corrutinas
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa oscura
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.size(170.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Título
            Text(
                text = "GES SPORT",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = CustomRed
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = CustomRed) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.88f),
                textStyle = TextStyle(color = CustomRed),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = SemiTransparentWhite,
                    focusedBorderColor = CustomRed,
                    unfocusedContainerColor = SemiTransparentWhite,
                    focusedContainerColor = SemiTransparentWhite
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = CustomRed) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.88f),
                textStyle = TextStyle(color = CustomRed),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = SemiTransparentWhite,
                    focusedBorderColor = CustomRed,
                    unfocusedContainerColor = SemiTransparentWhite,
                    focusedContainerColor = SemiTransparentWhite
                )
            )

            Spacer(modifier = Modifier.height(22.dp))

            // 🔴 BOTÓN PRINCIPAL: INICIAR SESIÓN
            Button(
                onClick = {
                    scope.launch {
                        val e = email.trim()
                        val p = password

                        // Validación básica: campos no vacíos
                        if (e.isBlank() || p.isBlank()) {
                            mensaje = "Los campos no pueden estar vacíos"
                            return@launch
                        }

                        // Buscamos el usuario en Room por email
                        // getUserByEmail ejecuta: SELECT * FROM usuarios WHERE email = :email LIMIT 1
                        val user = userRepository.getUserByEmail(e)

                        // Si no existe el email o la contraseña no coincide → error
                        if (user == null || user.password != p) {
                            mensaje = "Credenciales incorrectas"
                            return@launch
                        }

                        mensaje = ""
                        // Comprobamos el rol para decidir a qué pantalla navegar
                        val isAdmin = user.rol.trim().uppercase().startsWith("ADMIN")

                        if (isAdmin) {
                            // Admin → DashboardScreen (panel de gestión)
                            // popUpTo(inclusive=true) elimina el Login del backstack: el usuario no puede volver atrás
                            navController.navigate(Routes.Dashboard.route) {
                                popUpTo(Routes.Login.route) { inclusive = true }
                            }
                        } else {
                            // Jugador/Entrenador/Árbitro → HomeScreen con su userId como argumento de ruta
                            navController.navigate(Routes.Home.createRoute(user.id)) {
                                popUpTo(Routes.Login.route) { inclusive = true }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "Iniciar sesión",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ⚪ BOTÓN SECUNDARIO: CREAR CUENTA (JUGADOR)
            OutlinedButton(
                onClick = {
                    scope.launch {
                        val e = email.trim()
                        val p = password.trim()

                        if (e.isBlank() || p.isBlank()) {
                            mensaje = "Email y contraseña obligatorios"
                            return@launch
                        }

                        val existing = userRepository.getUserByEmail(e)
                        if (existing != null) {
                            mensaje = "Ese email ya está registrado"
                            return@launch
                        }

                        val newUser = User(
                            id = 0,
                            nombre = e.substringBefore("@"),
                            email = e,
                            password = p,
                            rol = "JUGADOR"
                        )

                        userRepository.addUser(newUser)
                        val created = userRepository.getUserByEmail(e)
                        mensaje = ""
                        navController.navigate(Routes.Home.createRoute(newUser.id)) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(20.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Text(
                    "Crear cuenta",
                    fontSize = 18.sp,
                    color = CustomRed,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = mensaje, color = Color.White)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Separador visual
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                Text(
                    "  O continuar con  ",
                    color = Color.White,
                    fontSize = 12.sp
                )
                Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ✅ Google / Apple
            SocialButton(
                text = "Conectar con Google",
                iconRes = R.drawable.google,
                onClick = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            SocialButton(
                text = "Conectar con AppleID",
                iconRes = R.drawable.apple,
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
fun SocialButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
    height: Dp = 60.dp
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = CustomRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
