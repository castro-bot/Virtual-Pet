package com.example.aplicacionmascotavirtual.ui.theme.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aplicacionmascotavirtual.screens.ConfiguracionScreen
import com.example.aplicacionmascotavirtual.screens.EleccionAnimalScreen
import com.example.aplicacionmascotavirtual.screens.MascotaScreen
import com.example.aplicacionmascotavirtual.data.Mascota
import com.example.aplicacionmascotavirtual.screens.Inicio

@Composable
fun AppNavigation(navController: NavHostController, mascota: Mascota) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            Inicio(navController)
        }
        composable("eleccion_animal") {
            EleccionAnimalScreen(
                mascota = mascota,
                onAnimalSeleccionado = { tipoAnimal ->
                    mascota.tipoAnimal = tipoAnimal
                    navController.navigate("configuracion")
                }
            )
        }
        composable("configuracion") { ConfiguracionScreen(mascota) }
        composable("mascota") { MascotaScreen(mascota) }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentBackStackEntry?.destination?.route == "eleccion_animal",
            onClick = { navController.navigate("eleccion_animal") },  // Cambio aquí
            label = { Text("Inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") }
        )
        NavigationBarItem(
            selected = navController.currentBackStackEntry?.destination?.route == "mascota",
            onClick = { navController.navigate("mascota") },
            label = { Text("Mascota") },
            icon = { Icon(Icons.Default.Star, contentDescription = "Mascota") }
        )
        NavigationBarItem(
            selected = navController.currentBackStackEntry?.destination?.route == "configuracion",
            onClick = { navController.navigate("configuracion") },
            label = { Text("Configuración") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") }
        )
    }
}
