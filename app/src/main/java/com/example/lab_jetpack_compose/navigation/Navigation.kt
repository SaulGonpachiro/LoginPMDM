package com.example.lab_jetpack_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_jetpack_compose.ui.backend.ges_instalacion.GesInstalacionScreen
import com.example.lab_jetpack_compose.ui.backend.ges_partidos.GesPartidosScreen
import com.example.lab_jetpack_compose.ui.backend.ges_reservas.GesReservaScreen
import com.example.lab_jetpack_compose.ui.backend.ges_team.GesTeamScreen
import com.example.lab_jetpack_compose.ui.backend.ges_user.GesUserScreen
import com.example.lab_jetpack_compose.ui.login.RegisterScreen
import com.example.lab_jetpack_compose.ui.login.WelcomeScreen
import com.example.lab_jetpack_compose.ui.login.components.AdminPanelScreen
import com.example.lab_jetpack_compose.ui.login.components.LoginScreen

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Welcome : Routes("welcome/{username}") {
        fun createRoute(username: String) = "welcome/$username"
    }

    object AdminPanel : Routes("admin_panel")

    // ✅ Backoffice
    object GesUsers : Routes("ges_users")
    object GesInstalacion : Routes("ges_instalacion")
    object GesTeam : Routes("ges_team")
    object GesPartidos : Routes("ges_partidos")
    object GesReservas : Routes("ges_reservas")

}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {
            LoginScreen(navController)
        }

        composable(Routes.Register.route) {
            RegisterScreen(navController)
        }

        composable(Routes.Welcome.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            WelcomeScreen(navController = navController, name = username)
        }

        composable(Routes.AdminPanel.route) {
            AdminPanelScreen(navController = navController)
        }

        composable(Routes.GesUsers.route) {
            GesUserScreen(navController = navController)
        }

        composable(Routes.GesInstalacion.route) {
            GesInstalacionScreen(navController = navController)
        }


        composable(Routes.GesPartidos.route) {
            GesPartidosScreen(navController = navController)
        }

        composable(Routes.GesReservas.route) {
            GesReservaScreen(navController = navController)
        }

        composable(Routes.GesTeam.route) {
            GesTeamScreen(navController = navController)
        }
    }
}
