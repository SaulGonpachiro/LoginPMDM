package com.example.lab_jetpack_compose.ui.backend.ges_reservas

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
import com.example.lab_jetpack_compose.models.Reserva

private val OverlayDark = Color(0xAA000000)
private val CardDark = Color(0xFF111827)
private val AccentPurple = Color(0xFF8B5CF6)
private val AccentRed = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GesReservaScreen(navController: NavHostController) {

    val app = LocalContext.current.applicationContext as LabApp
    val repo = app.container.reservaRepository

    val vm: GesReservaViewModel = viewModel(factory = GesReservaViewModelFactory(repo))

    var showForm by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingId by remember { mutableStateOf<Int?>(null) }

    var nombre by remember { mutableStateOf("") }
    var tipoPista by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }

    fun abrirCrear() {
        isEditing = false
        editingId = null
        nombre = ""
        tipoPista = ""
        fecha = ""
        hora = ""
        capacidad = ""
        showForm = true
    }

    fun abrirEditar(r: Reserva) {
        isEditing = true
        editingId = r.id
        nombre = r.nombre
        tipoPista = r.tipoPista
        fecha = r.fecha
        hora = r.hora
        capacidad = r.capacidad.toString()
        showForm = true
    }

    var reservaToDelete by remember { mutableStateOf<Reserva?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(color = OverlayDark, modifier = Modifier.fillMaxSize()) {
            Scaffold(
                containerColor = Color.Transparent,
                floatingActionButton = {
                    FloatingActionButton(onClick = { abrirCrear() }, containerColor = AccentPurple) {
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
                        text = "Gestión de reservas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(vm.reservas) { r ->
                            ReservaCard(
                                reserva = r,
                                onEdit = { abrirEditar(r) },
                                onDelete = { reservaToDelete = r }
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
                    text = if (isEditing) "Editar reserva" else "Crear reserva",
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
                        value = tipoPista,
                        onValueChange = { tipoPista = it },
                        label = { Text("Tipo de pista", color = Color.White) },
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
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { showForm = false }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            val capInt = capacidad.toIntOrNull() ?: 0
                            val reserva = Reserva(
                                id = editingId ?: 0,
                                nombre = nombre.trim(),
                                tipoPista = tipoPista.trim(),
                                fecha = fecha.trim(),
                                hora = hora.trim(),
                                capacidad = capInt
                            )

                            if (reserva.nombre.isNotBlank() &&
                                reserva.tipoPista.isNotBlank() &&
                                reserva.fecha.isNotBlank() &&
                                reserva.hora.isNotBlank()
                            ) {
                                if (isEditing) vm.update(reserva) else vm.add(reserva)
                                showForm = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentPurple, contentColor = Color.White)
                    ) {
                        Text(if (isEditing) "Guardar" else "Crear")
                    }
                }
            },
            dismissButton = {},
            containerColor = CardDark
        )
    }

    reservaToDelete?.let { r ->
        AlertDialog(
            onDismissRequest = { reservaToDelete = null },
            title = { Text("Borrar reserva", fontWeight = FontWeight.Bold, color = Color.White) },
            text = { Text("¿Seguro que quieres borrar la reserva de ${r.nombre}?", color = Color.White) },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { reservaToDelete = null }) {
                        Text("Cancelar", color = Color.LightGray)
                    }
                    Button(
                        onClick = {
                            vm.delete(r.id)
                            reservaToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentRed, contentColor = Color.White)
                    ) { Text("Borrar") }
                }
            },
            dismissButton = {},
            containerColor = CardDark
        )
    }
}

@Composable
private fun ReservaCard(
    reserva: Reserva,
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
                    text = reserva.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${reserva.tipoPista} • ${reserva.fecha} • ${reserva.hora} • cap ${reserva.capacidad}",
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
