package com.example.lab_jetpack_compose.navigation

/**
 * Navigation.kt
 *
 * Define todas las rutas de la aplicación y el grafo de navegación principal.
 *
 * Flujo de navegación:
 *   Login ──► (rol ADMIN)  ──► DashboardScreen
 *          └► (otros roles) ──► HomeScreen(userId)
 *
 * Desde DashboardScreen el admin navega a las pantallas de gestión (Ges*).
 * Desde HomeScreen el jugador/entrenador gestiona sus reservas y equipo.
 */

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_jetpack_compose.home.HomeScreen
import com.example.lab_jetpack_compose.ui.backend.dashboard.DashboardScreen
import com.example.lab_jetpack_compose.ui.backend.ges_instalacion.GesInstalacionScreen
import com.example.lab_jetpack_compose.ui.backend.ges_partidos.GesPartidosScreen
import com.example.lab_jetpack_compose.ui.backend.ges_reservas.GesReservaScreen
import com.example.lab_jetpack_compose.ui.backend.ges_team.GesTeamScreen
import com.example.lab_jetpack_compose.ui.backend.ges_user.GesUserScreen
import com.example.lab_jetpack_compose.ui.login.RegisterScreen
import com.example.lab_jetpack_compose.ui.login.components.LoginScreen

// Rutas tipadas para evitar strings sueltos en el código
sealed class Routes(val route: String) {

    // ── Autenticación ──────────────────────────────────────────────
    object Login    : Routes("login")
    object Register : Routes("register")

    // ── Frontoffice (Jugador / Entrenador) ─────────────────────────
    // userId se pasa como argumento para cargar los datos del usuario autenticado
    object Home : Routes("home/{userId}") {
        fun createRoute(userId: Int) = "home/$userId"
    }

    // ── Backoffice (Admin) ─────────────────────────────────────────
    object Dashboard     : Routes("dashboard")       // Pantalla principal del admin
    object GesUsers      : Routes("ges_users")       // CRUD de usuarios
    object GesInstalacion: Routes("ges_instalacion") // CRUD de pistas / instalaciones
    object GesTeam       : Routes("ges_team")        // CRUD de equipos
    object GesPartidos   : Routes("ges_partidos")    // CRUD de partidos
    object GesReservas   : Routes("ges_reservas")    // CRUD de reservas
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        // Pantalla de login — punto de entrada de la app
        composable(Routes.Login.route) {
            LoginScreen(navController)
        }

        // Registro de nuevo usuario (crea cuenta como JUGADOR)
        composable(Routes.Register.route) {
            RegisterScreen(navController)
        }

        // Dashboard del administrador
        composable(Routes.Dashboard.route) {
            DashboardScreen(navController = navController)
        }

        // Home del jugador/entrenador — recibe el id del usuario autenticado
        composable(Routes.Home.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
            HomeScreen(navController = navController, userId = userId)
        }

        // ── Pantallas de gestión (backoffice) ──────────────────────

        // Gestión de usuarios con filtro por rol
        composable(Routes.GesUsers.route) {
            GesUserScreen(navController = navController)
        }

        // Gestión de pistas e instalaciones
        composable(Routes.GesInstalacion.route) {
            GesInstalacionScreen(navController = navController)
        }

        // Gestión de partidos
        composable(Routes.GesPartidos.route) {
            GesPartidosScreen(navController = navController)
        }

        // Gestión de reservas por franja horaria
        composable(Routes.GesReservas.route) {
            GesReservaScreen(navController = navController)
        }

        // Gestión de equipos
        composable(Routes.GesTeam.route) {
            GesTeamScreen(navController = navController)
        }
    }
}
