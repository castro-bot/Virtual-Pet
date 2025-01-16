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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aplicacionmascotavirtual.data.Mascota
import com.example.aplicacionmascotavirtual.screens.ConfiguracionScreen
import com.example.aplicacionmascotavirtual.screens.EleccionAnimalScreen
import com.example.aplicacionmascotavirtual.screens.Inicio
import com.example.aplicacionmascotavirtual.screens.MascotaScreen
import com.example.aplicacionmascotavirtual.screens.Registro


@Composable
fun AppNavigation(navController: NavHostController, mascota: Mascota) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            Inicio(navController)
        }

        composable("registro") {
            Registro(navController)
        }

        composable("eleccion_animal") {
            EleccionAnimalScreen(
                mascota = mascota,
                onAnimalSeleccionado = { tipoAnimal ->
                    mascota.tipoAnimal = tipoAnimal
                    navController.navigate("mascota")
                }
            )
        }

        composable("mascota") {
            MascotaScreen(mascota)
        }

        composable("configuracion") {
            ConfiguracionScreen(mascota)
        }
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
                onClick = { navController.navigate("eleccion_animal") }
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