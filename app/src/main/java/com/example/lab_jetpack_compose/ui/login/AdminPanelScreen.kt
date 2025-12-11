package com.example.lab_jetpack_compose.ui.login.components

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
import com.example.lab_jetpack_compose.navigation.Routes    // 👈 IMPORT NUEVO

@Composable
fun AdminPanelScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
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

            // BARRA SUPERIOR
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

            // LOGO GRANDE CENTRADO
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(220.dp)
                    .height(140.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PANEL TRANSLÚCIDO (todo bajo el logo)
            Box(
                modifier = Modifier
                    .width(344.dp)
                    .height(460.dp)
            ) {
                // Fondo translúcido
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.6f)
                        .background(
                            color = SemiTransparentWhite,
                            shape = RoundedCornerShape(16.dp)
                        )
                )

                // CONTENIDO: iconos en forma de 5
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Fila superior: 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AdminTile(
                            title = "Jugadores",
                            iconRes = R.drawable.jugadores,
                            onClick = { navController.navigate(Routes.GesUsers.route) } // 👈 AQUÍ NAVEGAMOS
                        )
                        AdminTile(
                            title = "Equipos",
                            iconRes = R.drawable.equipos
                        )
                    }

                    // Centro: 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AdminTile(
                            title = "Partidos",
                            iconRes = R.drawable.partidos
                        )
                    }

                    // Fila inferior: 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AdminTile(
                            title = "Pistas",
                            iconRes = R.drawable.pistas
                        )
                        AdminTile(
                            title = "Reserva",
                            iconRes = R.drawable.reservas
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminTile(
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
