package com.example.lab_jetpack_compose.ui.backend.ges_partidos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.lab_jetpack_compose.models.Partido

// Estilo igual que GesUser / GesInstalacion
private val OverlayDark = Color(0xAA000000)
private val CardDark = Color(0xFF111827)
private val AccentPurple = Color(0xFF8B5CF6)
private val AccentRed = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesPartidosScreen(navController: NavHostController) {

    val app = LocalContext.current.applicationContext as LabApp
    val repo = app.container.partidoRepository

    val vm: GesPartidosViewModel = viewModel(
        factory = GesPartidosViewModelFactory(repo)
    )

    // Form create/edit
    var showForm by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingId by remember { mutableStateOf<Int?>(null) }

    var equipoLocal by remember { mutableStateOf("") }
    var equipoVisitante by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") } // ej: 2026-02-02
    var hora by remember { mutableStateOf("") }  // ej: 18:30

    fun abrirCrear() {
        isEditing = false
        editingId = null
        equipoLocal = ""
        equipoVisitante = ""
        fecha = ""
        hora = ""
        showForm = true
    }

    fun abrirEditar(p: Partido) {
        isEditing = true
        editingId = p.id
        equipoLocal = p.equipoLocal
        equipoVisitante = p.equipoVisitante
        fecha = p.fecha
        hora = p.hora
        showForm = true
    }

    var partidoToDelete by remember { mutableStateOf<Partido?>(null) }

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
                        text = "Gestión de partidos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.partidos) { p ->
                            PartidoCard(
                                partido = p,
                                onEdit = { abrirEditar(p) },
                                onDelete = { partidoToDelete = p }
                            )
                        }
                    }
                }
            }
        }
    }

    // =================== DIALOG FORM ===================
    if (showForm) {
        val scrollState = rememberScrollState()

        AlertDialog(
            onDismissRequest = { showForm = false },
            title = {
                Text(
                    text = if (isEditing) "Editar partido" else "Crear partido",
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
                        value = equipoLocal,
                        onValueChange = { equipoLocal = it },
                        label = { Text("Equipo local", color = Color.White) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = equipoVisitante,
                        onValueChange = { equipoVisitante = it },
                        label = { Text("Equipo visitante", color = Color.White) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha (ej: 2026-02-02)", color = Color.White) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White)
                    )

                    OutlinedTextField(
                        value = hora,
                        onValueChange = { hora = it },
                        label = { Text("Hora (ej: 18:30)", color = Color.White) },
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
                            val partido = Partido(
                                id = editingId ?: 0,
                                equipoLocal = equipoLocal.trim(),
                                equipoVisitante = equipoVisitante.trim(),
                                fecha = fecha.trim(),
                                hora = hora.trim()
                            )

                            // Validación mínima
                            if (partido.equipoLocal.isNotBlank() &&
                                partido.equipoVisitante.isNotBlank() &&
                                partido.fecha.isNotBlank() &&
                                partido.hora.isNotBlank()
                            ) {
                                if (isEditing) vm.update(partido) else vm.add(partido)
                                showForm = false
                            }
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

    // =================== DIALOG DELETE ===================
    partidoToDelete?.let { p ->
        AlertDialog(
            onDismissRequest = { partidoToDelete = null },
            title = { Text("Borrar partido", fontWeight = FontWeight.Bold, color = Color.White) },
            text = {
                Text(
                    "¿Seguro que quieres borrar ${p.equipoLocal} vs ${p.equipoVisitante} (${p.fecha} ${p.hora})?",
                    color = Color.White
                )
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { partidoToDelete = null }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            vm.delete(p.id)
                            partidoToDelete = null
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
private fun PartidoCard(
    partido: Partido,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        color = CardDark,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${partido.equipoLocal} vs ${partido.equipoVisitante}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${partido.fecha} • ${partido.hora}",
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
