package com.example.aplicacionmascotavirtual.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.componentes.*
import com.example.aplicacionmascotavirtual.viewmodel.AuthViewModel
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracionScreen(navController: NavController) {
    val mascotaViewModel: MascotaViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val mascotaSeleccionada by mascotaViewModel.mascotaSeleccionada.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(16.dp)
        ) {
            Text(
                "Configuración",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            mascotaSeleccionada?.let { mascota ->
                var nombre by remember { mutableStateOf(mascota.nombre) }

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la mascota") },

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar imagen de la mascota
                val imageRes = when (mascota.imagen) {
                    "zorro" -> R.drawable.zorro
                    "pajaro" -> R.drawable.pajaro
                    "huron" -> R.drawable.huron
                    else -> R.drawable.repuesto
                }

                Image(
                    painter = painterResource(imageRes),
                    contentDescription = "Mascota",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val mascotaActualizada = mascota.copy(nombre = nombre)
                        mascotaViewModel.actualizarMascota(mascotaActualizada)
                        navController.navigateUp()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Guardar cambios")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón para cerrar sesión
            Button(
                onClick = {
                    authViewModel.cerrarSesion()
                    navController.navigate("iniciarsesion") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cerrar Sesión")
            }

            // Botón para crear nueva mascota
            Button(
                onClick = { navController.navigate("EleccionAnimal") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Nueva Mascota")
            }
        }
    }
}