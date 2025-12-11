package com.example.lab_jetpack_compose.ui.backend.ges_user

import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.models.User
import com.example.lab_jetpack_compose.models.UserRoles

// Colores base
private val OverlayDark = Color(0xAA000000)   // negro semitransparente sobre la imagen
private val CardDark = Color(0xFF111827)
private val AccentPurple = Color(0xFF8B5CF6)
private val AccentRed = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesUserScreen(
    navController: NavHostController
) {
    val viewModel: GesUserViewModel = viewModel()

    val users = viewModel.users
    val selectedRole = viewModel.selectedRole

    // ==== Estado del diálogo de formulario (crear / editar) ====
    var showForm by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingId by remember { mutableStateOf<Int?>(null) }

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf<String?>(null) }

    fun abrirCrear() {
        isEditing = false
        editingId = null
        nombre = ""
        email = ""
        password = ""
        // Si estás filtrando por rol, usamos ese rol por defecto
        rol = selectedRole ?: "JUGADOR"
        showForm = true
    }

    fun abrirEditar(user: User) {
        isEditing = true
        editingId = user.id
        nombre = user.nombre
        email = user.email
        password = user.password
        rol = user.rol
        showForm = true
    }

    // ==== Estado para confirmar borrado ====
    var userToDelete by remember { mutableStateOf<User?>(null) }

    // Filtrado por rol en memoria
    val filteredUsers = if (selectedRole == null) {
        users
    } else {
        users.filter { it.rol == selectedRole }
    }

    // ================= FONDO CON IMAGEN =================
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa oscura encima para que se lea todo
        Surface(
            color = OverlayDark,
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { abrirCrear() },
                        containerColor = AccentPurple
                    ) {
                        Text("➕", fontSize = 24.sp, color = Color.White)
                    }
                }
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {

                    // ===== TÍTULO PANEL =====
                    Text(
                        text = "Gestión de usuarios",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // ===== FILTROS POR ROL =====
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedRole == null,
                            onClick = { viewModel.onRoleSelected(null) },
                            label = { Text("Todos") },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.Transparent,
                                labelColor = Color.White,
                                selectedContainerColor = AccentPurple,
                                selectedLabelColor = Color.White
                            )
                        )

                        UserRoles.allRoles.forEach { (value, label) ->
                            FilterChip(
                                selected = selectedRole == value,
                                onClick = { viewModel.onRoleSelected(value) },
                                label = { Text(label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.Transparent,
                                    labelColor = Color.White,
                                    selectedContainerColor = AccentPurple,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    // ===== LISTA DE USUARIOS =====
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredUsers) { user ->
                            UserCard(
                                user = user,
                                onEdit = { abrirEditar(user) },
                                onDelete = { userToDelete = user }
                            )
                        }
                    }
                }
            }
        }
    }

    // ================= DIÁLOGO FORMULARIO (CREAR / EDITAR) =================
    if (showForm) {
        val scrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = { showForm = false },
            title = {
                Text(
                    text = if (isEditing) "Editar usuario" else "Crear usuario",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                        .verticalScroll(scrollState)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )


                    Text(
                        "Rol",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        UserRoles.allRoles.forEach { (value, label) ->
                            FilterChip(
                                selected = rol == value,
                                onClick = { rol = value },
                                label = { Text(label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = Color.Transparent,
                                    labelColor = Color.White,
                                    selectedContainerColor = AccentPurple,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { showForm = false }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            val finalRol = rol ?: "JUGADOR"

                            val user = User(
                                id = editingId ?: 0,
                                nombre = nombre,
                                email = email,
                                password = password,
                                rol = finalRol
                            )

                            if (isEditing) {
                                viewModel.updateUser(user)
                            } else {
                                viewModel.addUser(user)
                            }

                            showForm = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentPurple,
                            contentColor = Color.White
                        )
                    ) {
                        Text(if (isEditing) "Guardar" else "Crear")
                    }
                }
            },
            dismissButton = {},
            containerColor = CardDark
        )
    }

    // ================= DIÁLOGO CONFIRMAR BORRADO =================
    userToDelete?.let { user ->
        AlertDialog(
            onDismissRequest = { userToDelete = null },
            title = {
                Text(
                    "Borrar usuario",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Text(
                    "¿Seguro que quieres borrar a ${user.nombre}?",
                    color = Color.White
                )
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { userToDelete = null }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            viewModel.deleteUser(user.id)
                            userToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentRed,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Borrar")
                    }
                }
            },
            dismissButton = {},
            containerColor = CardDark
        )
    }
}
