package com.example.aplicacionmascotavirtual.navegacion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aplicacionmascotavirtual.screens.ConfiguracionScreen
import com.example.aplicacionmascotavirtual.screens.EleccionAnimal
//import com.example.aplicacionmascotavirtual.screens.HistorialScreen
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
            MascotaScreen(navController, context = navController.context)
        }

        composable("iniciarsesion") {
            InicioSesion(
                navController = navController,
                viewModel = viewModel()

            )
        }

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
//            HistorialScreen(navController)
//        }
    }
}

