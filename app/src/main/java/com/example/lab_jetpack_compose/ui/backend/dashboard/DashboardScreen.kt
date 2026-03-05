package com.example.lab_jetpack_compose.ui.backend.dashboard

/**
 * DashboardScreen
 *
 * Pantalla principal del administrador (ADMIN_DEPORTIVO).
 * Se accede tras el login si el rol del usuario es ADMIN.
 *
 * Contiene 5 accesos directos a las pantallas de gestión:
 *   - Jugadores (GesUserScreen)
 *   - Equipos   (GesTeamScreen)
 *   - Partidos  (GesPartidosScreen)
 *   - Pistas    (GesInstalacionScreen)
 *   - Reservas  (GesReservaScreen)
 *
 * Diseño: imagen de fondo + logo centrado + grid de tiles oscuros con icono y etiqueta.
 */

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab_jetpack_compose.R
import com.example.lab_jetpack_compose.navigation.Routes
import com.example.lab_jetpack_compose.ui.theme.CustomRed
import com.example.lab_jetpack_compose.ui.theme.SemiTransparentWhite

@Composable
fun DashboardScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        // Imagen de fondo deportiva
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo Admin",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Barra superior con título del panel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(CustomRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Panel de Administración",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logo grande centrado
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "Logo GES SPORT",
                modifier = Modifier
                    .width(220.dp)
                    .height(140.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Panel translúcido con los tiles de navegación
            Box(
                modifier = Modifier
                    .width(344.dp)
                    .height(460.dp)
            ) {
                // Fondo translúcido del panel
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.6f)
                        .background(
                            color = SemiTransparentWhite,
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                // Grid de tiles en distribución 2-1-2
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Fila superior: Jugadores y Equipos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardTile(
                            title = "Jugadores",
                            iconRes = R.drawable.jugadores,
                            onClick = { navController.navigate(Routes.GesUsers.route) }
                        )
                        DashboardTile(
                            title = "Equipos",
                            iconRes = R.drawable.equipos,
                            onClick = { navController.navigate(Routes.GesTeam.route) }
                        )
                    }

                    // Centro: Partidos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DashboardTile(
                            title = "Partidos",
                            iconRes = R.drawable.partidos,
                            onClick = { navController.navigate(Routes.GesPartidos.route) }
                        )
                    }

                    // Fila inferior: Pistas y Reservas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DashboardTile(
                            title = "Pistas",
                            iconRes = R.drawable.pistas,
                            onClick = { navController.navigate(Routes.GesInstalacion.route) }
                        )
                        DashboardTile(
                            title = "Reservas",
                            iconRes = R.drawable.reservas,
                            onClick = { navController.navigate(Routes.GesReservas.route) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tile individual del dashboard.
 * Muestra un icono y una etiqueta. Al hacer clic navega a la pantalla correspondiente.
 */
@Composable
private fun DashboardTile(
    title: String,
    iconRes: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.85f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(42.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = CustomRed
            )
        }
    }
}
