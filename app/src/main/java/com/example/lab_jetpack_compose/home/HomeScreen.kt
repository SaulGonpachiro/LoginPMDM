package com.example.lab_jetpack_compose.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.LabApp
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.models.EntrenamientoEquipo
import com.example.lab_jetpack_compose.models.Reserva
import com.example.lab_jetpack_compose.navigation.Routes
import com.example.lab_jetpack_compose.ui.login.components.CustomRed
import com.example.lab_jetpack_compose.ui.login.components.PrimaryRed
import com.example.lab_jetpack_compose.ui.login.components.SemiTransparentWhite
import kotlinx.coroutines.launch

private enum class HomeTab { INICIO, RESERVAS, EQUIPO, PERFIL }

private val CardShape = RoundedCornerShape(18.dp)
private val PanelShape = RoundedCornerShape(18.dp)
private val PanelBg = Color.Black.copy(alpha = 0.85f)
private val TextMuted = Color.White.copy(alpha = 0.75f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userId: Int
) {
    val context = LocalContext.current
    val app = context.applicationContext as LabApp

    val userRepository = app.container.userRepository
    val reservaRepository = app.container.reservaRepository
    val entrenamientoRepo = app.container.entrenamientoEquipoRepository
    val inscripcionRepo = app.container.inscripcionEntrenamientoRepository

    val scope = rememberCoroutineScope()

    var nombre by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.INICIO) }

    // Dialog crear reserva
    var showCreateReserva by rememberSaveable { mutableStateOf(false) }
    var formTipoPista by rememberSaveable { mutableStateOf("") }
    var formFecha by rememberSaveable { mutableStateOf("") }
    var formHora by rememberSaveable { mutableStateOf("") }
    var formCapacidad by rememberSaveable { mutableStateOf("4") }
    var formError by rememberSaveable { mutableStateOf("") }

    // Dialog crear entrenamiento equipo (solo entrenador)
    var showCreateEntreno by rememberSaveable { mutableStateOf(false) }
    var formEquipoNombre by rememberSaveable { mutableStateOf("") }
    var formEquipoTipoPista by rememberSaveable { mutableStateOf("") }
    var formEquipoFecha by rememberSaveable { mutableStateOf("") }
    var formEquipoHora by rememberSaveable { mutableStateOf("") }
    var formEquipoCapacidad by rememberSaveable { mutableStateOf("4") }
    var formEquipoError by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(userId) {
        val u = userRepository.getUserById(userId)
        nombre = u?.nombre ?: ""
        rol = u?.rol ?: ""
    }

    val rolUpper = rol.trim().uppercase()
    val isEntrenador = rolUpper == "ENTRENADOR"
    val rolLabel = if (isEntrenador) "Entrenador" else "Jugador"

    // Reservas individuales: todos ven solo las suyas
    val reservas by reservaRepository.observeAll().collectAsState(initial = emptyList())
    val reservasVisibles = remember(reservas, nombre) {
        reservas.filter { it.nombre == nombre }
    }

    // Entrenos + inscripciones del jugador
    val entrenos by entrenamientoRepo.observeAll().collectAsState(initial = emptyList())
    val inscripcionesJugador by inscripcionRepo.observeByJugador(nombre).collectAsState(initial = emptyList())
    val misEntrenosIds = remember(inscripcionesJugador) {
        inscripcionesJugador.map { it.entrenamientoId }.toSet()
    }

    @Composable
    fun DarkCard(content: @Composable ColumnScope.() -> Unit) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = CardShape
        ) {
            Column(modifier = Modifier.padding(14.dp), content = content)
        }
    }

    @Composable
    fun PanelColumn(content: @Composable ColumnScope.() -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(PanelBg, PanelShape)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }

    @Composable
    fun darkFieldColors() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        disabledTextColor = Color.White.copy(alpha = 0.6f),
        focusedBorderColor = CustomRed,
        unfocusedBorderColor = Color.White.copy(alpha = 0.35f),
        disabledBorderColor = Color.White.copy(alpha = 0.2f),
        focusedLabelColor = CustomRed,
        unfocusedLabelColor = Color.White.copy(alpha = 0.8f),
        cursorColor = CustomRed,
        focusedContainerColor = Color.Black,
        unfocusedContainerColor = Color.Black,
        disabledContainerColor = Color.Black,
        focusedPlaceholderColor = Color.White.copy(alpha = 0.55f),
        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.55f)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("GES SPORT", fontWeight = FontWeight.Black, color = Color.Black)
                            Text(rolLabel, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.75f))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = CustomRed),
                    actions = {
                        IconButton(onClick = {
                            val uri = Uri.parse("geo:0,0?q=Centro%20Deportivo")
                            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            runCatching { context.startActivity(intent) }
                                .recoverCatching { context.startActivity(Intent(Intent.ACTION_VIEW, uri)) }
                        }) {
                            Icon(Icons.Filled.LocationOn, contentDescription = "Mapa", tint = Color.Black)
                        }

                        IconButton(onClick = {
                            navController.navigate(Routes.Login.route) {
                                popUpTo(Routes.Login.route) { inclusive = true }
                            }
                        }) {
                            Icon(Icons.Filled.Logout, contentDescription = "Salir", tint = Color.Black)
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.Black, tonalElevation = 8.dp) {

                    @Composable
                    fun navItem(tab: HomeTab, label: String, icon: @Composable () -> Unit) {
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { icon() },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CustomRed,
                                selectedTextColor = CustomRed,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }

                    navItem(HomeTab.INICIO, "Inicio") { Icon(Icons.Filled.Home, null) }
                    navItem(HomeTab.RESERVAS, "Reservas") { Icon(Icons.Filled.Event, null) }
                    navItem(HomeTab.EQUIPO, "Equipo") { Icon(Icons.Filled.Groups, null) }
                    navItem(HomeTab.PERFIL, "Perfil") { Icon(Icons.Filled.Person, null) }
                }
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.65f)
                        .background(SemiTransparentWhite, RoundedCornerShape(18.dp))
                )

                when (selectedTab) {
                    HomeTab.INICIO -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            item {
                                DarkCard {
                                    Text(
                                        text = "¡Bienvenido, $nombre!",
                                        color = CustomRed,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = if (isEntrenador) "Gestiona entrenamientos del centro"
                                        else "Gestiona tus reservas y entrenamientos",
                                        color = Color.White
                                    )
                                }
                            }

                            item {
                                DarkCard {
                                    Text("Información general", fontWeight = FontWeight.Bold, color = CustomRed)
                                    Spacer(Modifier.height(6.dp))
                                    Text("Horarios: 08:00 - 22:00", color = Color.White)
                                    Text("Deportes: Tenis, Pádel, etc.", color = Color.White)
                                    Text("Puedes reservar y cancelar", color = Color.White)
                                }
                            }

                            item {
                                DarkCard {
                                    Text("Próximas reservas (tuyas)", fontWeight = FontWeight.Bold, color = CustomRed)
                                    Spacer(Modifier.height(8.dp))
                                    if (reservasVisibles.isEmpty()) {
                                        Text("No hay reservas.", color = Color.White)
                                    } else {
                                        reservasVisibles.take(3).forEach { r ->
                                            Text(
                                                "• ${r.tipoPista} - ${r.fecha} ${r.hora} (Cap: ${r.capacidad})",
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }

                            // ✅ FIX: En Inicio, “Reservas con equipo”
                            item {
                                DarkCard {
                                    Text("Reservas con equipo", fontWeight = FontWeight.Bold, color = CustomRed)
                                    Spacer(Modifier.height(8.dp))

                                    val listaInicio = if (isEntrenador) {
                                        // Entrenador: muestra sesiones creadas por él
                                        entrenos.filter { it.creadoPor == nombre }
                                    } else {
                                        // Jugador: muestra sesiones donde está inscrito
                                        entrenos.filter { it.id in misEntrenosIds }
                                    }

                                    if (listaInicio.isEmpty()) {
                                        Text(
                                            text = if (isEntrenador)
                                                "No has creado sesiones de equipo todavía."
                                            else
                                                "No estás inscrito a entrenamientos de equipo.",
                                            color = Color.White
                                        )
                                    } else {
                                        listaInicio.take(3).forEach { e ->
                                            Text(
                                                "• ${e.equipoNombre} - ${e.tipoPista} - ${e.fecha} ${e.hora}",
                                                color = Color.White
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(10.dp))
                                    Text(
                                        text = if (isEntrenador)
                                            "Ve a la pestaña “Equipo” para crear sesiones."
                                        else
                                            "Ve a la pestaña “Equipo” para apuntarte o darte de baja.",
                                        fontSize = 12.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }

                    HomeTab.RESERVAS -> {
                        PanelColumn {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Reservas", fontWeight = FontWeight.Black, fontSize = 18.sp, color = CustomRed)

                                Button(
                                    onClick = {
                                        formTipoPista = ""
                                        formFecha = ""
                                        formHora = ""
                                        formCapacidad = "4"
                                        formError = ""
                                        showCreateReserva = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text("Nueva", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }

                            if (reservasVisibles.isEmpty()) {
                                Text("No hay reservas.", color = Color.White)
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(reservasVisibles) { r ->
                                        DarkCard {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(Modifier.weight(1f)) {
                                                    Text(
                                                        "${r.tipoPista} • ${r.fecha} ${r.hora}",
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White
                                                    )
                                                    Text("Cap: ${r.capacidad}", fontSize = 12.sp, color = TextMuted)
                                                }
                                                TextButton(onClick = { scope.launch { reservaRepository.delete(r.id) } }) {
                                                    Text("Cancelar", color = CustomRed)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    HomeTab.EQUIPO -> {
                        PanelColumn {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Equipo", fontWeight = FontWeight.Black, fontSize = 18.sp, color = CustomRed)

                                if (isEntrenador) {
                                    Button(
                                        onClick = {
                                            formEquipoNombre = ""
                                            formEquipoTipoPista = ""
                                            formEquipoFecha = ""
                                            formEquipoHora = ""
                                            formEquipoCapacidad = "4"
                                            formEquipoError = ""
                                            showCreateEntreno = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text("Crear sesión", color = Color.Black, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            // (Esta pestaña se queda igual: muestra todas las sesiones)
                            if (entrenos.isEmpty()) {
                                Text("No hay sesiones de equipo.", color = Color.White)
                                if (!isEntrenador) {
                                    Text("Pide al entrenador que cree una sesión.", fontSize = 12.sp, color = TextMuted)
                                }
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(entrenos) { e ->
                                        val estoy = e.id in misEntrenosIds

                                        DarkCard {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(Modifier.weight(1f)) {
                                                    Text("${e.equipoNombre} • ${e.tipoPista}", fontWeight = FontWeight.Bold, color = Color.White)
                                                    Text("${e.fecha} ${e.hora}", fontSize = 12.sp, color = TextMuted)
                                                }

                                                if (!isEntrenador) {
                                                    if (!estoy) {
                                                        Button(
                                                            onClick = { scope.launch { inscripcionRepo.apuntar(e.id, nombre) } },
                                                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                                                            shape = RoundedCornerShape(50)
                                                        ) { Text("Apuntarme", color = Color.Black, fontWeight = FontWeight.Bold) }
                                                    } else {
                                                        TextButton(onClick = { scope.launch { inscripcionRepo.baja(e.id, nombre) } }) {
                                                            Text("Darme de baja", color = CustomRed)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    HomeTab.PERFIL -> {
                        PanelColumn {
                            DarkCard {
                                Text("Perfil", fontWeight = FontWeight.Black, fontSize = 18.sp, color = CustomRed)
                                Spacer(Modifier.height(8.dp))
                                Text("Nombre: $nombre", color = Color.White)
                                Text("Rol: $rol", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog nueva reserva
    if (showCreateReserva) {
        AlertDialog(
            onDismissRequest = { showCreateReserva = false },
            containerColor = Color.Black,
            titleContentColor = CustomRed,
            textContentColor = Color.White,
            title = { Text("Nueva reserva") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = formTipoPista,
                        onValueChange = { formTipoPista = it },
                        label = { Text("Tipo (Tenis / Pádel...)") },
                        placeholder = { Text("Ej: Pádel") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formFecha,
                        onValueChange = { formFecha = it },
                        label = { Text("Fecha (YYYY-MM-DD)") },
                        placeholder = { Text("Ej: 2026-02-19") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formHora,
                        onValueChange = { formHora = it },
                        label = { Text("Hora (HH:MM)") },
                        placeholder = { Text("Ej: 18:30") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formCapacidad,
                        onValueChange = { formCapacidad = it.filter { c -> c.isDigit() } },
                        label = { Text("Capacidad") },
                        placeholder = { Text("Ej: 4") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )

                    if (formError.isNotBlank()) {
                        Text(formError, color = Color.Red, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val n = nombre.trim()
                        val t = formTipoPista.trim()
                        val f = formFecha.trim()
                        val h = formHora.trim()
                        val cap = formCapacidad.toIntOrNull()

                        if (n.isBlank() || t.isBlank() || f.isBlank() || h.isBlank() || cap == null) {
                            formError = "Rellena todos los campos correctamente"
                            return@Button
                        }

                        scope.launch {
                            reservaRepository.add(
                                Reserva(
                                    id = 0,
                                    nombre = n,
                                    tipoPista = t,
                                    fecha = f,
                                    hora = h,
                                    capacidad = cap
                                )
                            )
                            showCreateReserva = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                    shape = RoundedCornerShape(50)
                ) { Text("Crear", color = Color.Black, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showCreateReserva = false }) { Text("Cancelar", color = CustomRed) }
            }
        )
    }

    // Dialog crear sesión equipo
    if (showCreateEntreno && isEntrenador) {
        AlertDialog(
            onDismissRequest = { showCreateEntreno = false },
            containerColor = Color.Black,
            titleContentColor = CustomRed,
            textContentColor = Color.White,
            title = { Text("Crear sesión de equipo") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    OutlinedTextField(
                        value = formEquipoNombre,
                        onValueChange = { formEquipoNombre = it },
                        label = { Text("Nombre del equipo") },
                        placeholder = { Text("Ej: Pádel Avanzado") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formEquipoTipoPista,
                        onValueChange = { formEquipoTipoPista = it },
                        label = { Text("Tipo (Tenis / Pádel...)") },
                        placeholder = { Text("Ej: Tenis") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formEquipoFecha,
                        onValueChange = { formEquipoFecha = it },
                        label = { Text("Fecha (YYYY-MM-DD)") },
                        placeholder = { Text("Ej: 2026-02-19") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formEquipoHora,
                        onValueChange = { formEquipoHora = it },
                        label = { Text("Hora (HH:MM)") },
                        placeholder = { Text("Ej: 19:00") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )
                    OutlinedTextField(
                        value = formEquipoCapacidad,
                        onValueChange = { formEquipoCapacidad = it.filter { c -> c.isDigit() } },
                        label = { Text("Capacidad") },
                        placeholder = { Text("Ej: 8") },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White),
                        colors = darkFieldColors()
                    )

                    if (formEquipoError.isNotBlank()) {
                        Text(formEquipoError, color = Color.Red, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val eq = formEquipoNombre.trim()
                        val t = formEquipoTipoPista.trim()
                        val f = formEquipoFecha.trim()
                        val h = formEquipoHora.trim()
                        val cap = formEquipoCapacidad.toIntOrNull()

                        if (eq.isBlank() || t.isBlank() || f.isBlank() || h.isBlank() || cap == null) {
                            formEquipoError = "Rellena todos los campos correctamente"
                            return@Button
                        }

                        scope.launch {
                            entrenamientoRepo.add(
                                EntrenamientoEquipo(
                                    id = 0,
                                    equipoNombre = eq,
                                    tipoPista = t,
                                    fecha = f,
                                    hora = h,
                                    capacidad = cap,
                                    creadoPor = nombre
                                )
                            )
                            showCreateEntreno = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                    shape = RoundedCornerShape(50)
                ) { Text("Crear", color = Color.Black, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showCreateEntreno = false }) { Text("Cancelar", color = CustomRed) }
            }
        )
    }
}