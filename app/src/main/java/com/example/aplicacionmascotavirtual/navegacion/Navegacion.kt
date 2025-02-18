package com.example.aplicacionmascotavirtual.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aplicacionmascotavirtual.screens.ConfiguracionScreen
import com.example.aplicacionmascotavirtual.screens.EleccionAnimal
import com.example.aplicacionmascotavirtual.screens.MascotaScreen
import com.example.aplicacionmascotavirtual.vistas.Registro
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel
import com.example.aplicacionmascotavirtual.vistas.InicioSesion
import com.example.aplicacionmascotavirtual.vistas.JuegoAdivinarVista
import com.example.aplicacionmascotavirtual.vistas.LogrosScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation(navController: NavHostController) {

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        "mascota"
    } else {
        "iniciarsesion"
    }

    NavHost(navController = navController, startDestination) {
        composable("mascota") {
            MascotaScreen(navController, context = navController.context) // ✅ Enviamos todas las mascotas
        }

        composable("iniciarsesion") {
            InicioSesion(
                navController = navController,
                viewModel = viewModel()

            )
        }

        // En Navegacion.kt, modificar la ruta de logros
        // En Navegacion.kt
        composable("logros") {
            val mascotaViewModel: MascotaViewModel = viewModel()
            LogrosScreen(
                achievementManager = mascotaViewModel.getAchievementManager(),
                navController = navController
            )
        }

        composable("registro") {
            Registro(
                navController = navController,
            )
        }

        composable("EleccionAnimal") {
            EleccionAnimal(

                navController = navController,

                )
        }

        composable("configuracion") {
            ConfiguracionScreen(navController)
        }

        composable("JuegoAdivinarVista") {
            JuegoAdivinarVista(navController)
        }

//        composable("historial") {
//            HistorialScreen(navController, historialInteracciones)
//        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    if (currentRoute !in listOf("inicio", "registro")) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Inicio") },
                selected = currentRoute == "eleccion_animal",
                onClick = { navController.navigate("EleccionAnimal") }
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Star, contentDescription = "Mascota") },
                label = { Text("Mascota") },
                selected = currentRoute == "mascota",
                onClick = { navController.navigate("mascota") }
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
                label = { Text("Configuración") },
                selected = currentRoute == "configuracion",
                onClick = { navController.navigate("configuracion") }
            )
        }
    }
}