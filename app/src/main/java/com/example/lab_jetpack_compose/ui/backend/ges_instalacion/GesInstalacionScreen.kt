package com.example.lab_jetpack_compose.ui.backend.ges_instalacion

/**
 * GesInstalacionScreen  [BACKOFFICE - Solo accesible desde DashboardScreen]
 *
 * Pantalla de gestión de instalaciones y pistas deportivas (CRUD completo).
 * El admin puede dar de alta pistas nuevas, editarlas o eliminarlas.
 *
 * Campos de una instalación: nombre, tipo (FUTBOL/PADEL/TENIS...), horario disponible y capacidad.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.LabApp
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.models.Instalacion
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.clickable
import com.example.lab_jetpack_compose.ui.theme.OverlayDark
import com.example.lab_jetpack_compose.ui.theme.CardDark
import com.example.lab_jetpack_compose.ui.theme.AccentPurple
import com.example.lab_jetpack_compose.ui.theme.AccentRed




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesInstalacionScreen(navController: NavHostController) {

    val app = LocalContext.current.applicationContext as LabApp
    // Repositorio de instalaciones del AppContainer
    val repo = app.container.instalacionRepository

    // ViewModel con Factory para inyectar el repositorio
    val vm: GesInstalacionViewModel = viewModel(
        factory = GesInstalacionViewModelFactory(repo)
    )

    var showForm by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingId by remember { mutableStateOf<Int?>(null) }

    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("FUTBOL") }
    var horario by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }

    // Limpia campos y abre el dialog en modo creación
    fun abrirCrear() {
        isEditing = false
        editingId = null
        nombre = ""
        tipo = "FUTBOL"
        horario = ""
        capacidad = ""
        showForm = true
    }

    // Pre-rellena el formulario con los datos de la instalación a editar
    fun abrirEditar(inst: Instalacion) {
        isEditing = true
        editingId = inst.id
        nombre = inst.nombre
        tipo = inst.tipo
        horario = inst.horario
        capacidad = inst.capacidad.toString()
        showForm = true
    }

    // Instalación pendiente de confirmar el borrado
    var instToDelete by remember { mutableStateOf<Instalacion?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Gestión de pistas / instalaciones",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.instalaciones) { inst ->
                            InstalacionCard(
                                inst = inst,
                                onEdit = { abrirEditar(inst) },
                                onDelete = { instToDelete = inst }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showForm) {
        val scrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = { showForm = false },
            title = {
                Text(
                    text = if (isEditing) "Editar instalación" else "Crear instalación",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 430.dp)
                        .verticalScroll(scrollState)
                ) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre", color = Color.White) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = tipo,
                        onValueChange = { tipo = it },
                        label = { Text("Tipo de pista (FUTBOL, PADEL...)", color = Color.White) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = horario,
                        onValueChange = { horario = it },
                        label = { Text("Horario (ej: 09:00 - 22:00)", color = Color.White) }, // ✅ antes decía Ubicación
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = capacidad,
                        onValueChange = { capacidad = it.filter { c -> c.isDigit() } },
                        label = { Text("Capacidad", color = Color.White) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )
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
                            val capInt = capacidad.toIntOrNull() ?: 0
                            val inst = Instalacion(
                                id = editingId ?: 0,
                                nombre = nombre.trim(),
                                tipo = tipo.trim().ifBlank { "FUTBOL" },
                                horario = horario.trim().ifBlank { "No definido" },
                                capacidad = capInt
                            )

                            // Solo guardamos si el nombre no está vacío
                            if (inst.nombre.isNotBlank()) {
                                if (isEditing) vm.update(inst) else vm.add(inst)
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

    instToDelete?.let { inst ->
        AlertDialog(
            onDismissRequest = { instToDelete = null },
            title = { Text("Borrar instalación", fontWeight = FontWeight.Bold, color = Color.White) },
            text = { Text("¿Seguro que quieres borrar '${inst.nombre}'?", color = Color.White) },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { instToDelete = null }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            // Borrado confirmado
                            vm.delete(inst.id)
                            instToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentRed,
                            contentColor = Color.White
                        )
                    ) { Text("Borrar") }
                }
            },
            dismissButton = {},
            containerColor = CardDark
        )
    }
}

@Composable
private fun InstalacionCard(
    inst: Instalacion,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF111827)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = inst.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${inst.tipo} • ${inst.horario} • cap ${inst.capacidad}",
                    color = Color(0xFF9CA3AF),
                    fontSize = 13.sp
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "✏️",
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { onEdit() }
                )

                Text(
                    text = "🗑️",
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        }
    }
}

