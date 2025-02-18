package com.example.aplicacionmascotavirtual.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.models.Mascota
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel
import kotlinx.coroutines.launch

@Composable
fun EleccionAnimal(navController: NavController) {
    val mascotaViewModel: MascotaViewModel = viewModel()
    var nombreMascota by remember { mutableStateOf("") }
    val error by mascotaViewModel.error.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Elige tu mascota",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = nombreMascota,
                onValueChange = { nombreMascota = it },
                label = { Text("Nombre de tu mascota") },
                modifier = Modifier.fillMaxWidth()
            )

            if (!error.isNullOrEmpty()) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listOf(
                    Triple("Zorro", R.drawable.zorro, "zorro"),
                    Triple("Pájaro", R.drawable.pajaro, "pajaro"),
                    Triple("Hurón", R.drawable.huron, "huron")
                )) { (nombre, imagen, tipo) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(imagen),
                            contentDescription = nombre,
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (nombreMascota.isNotEmpty()) {
                                    isLoading = true
                                    mascotaViewModel.limpiarError()
                                    scope.launch {
                                        val nuevaMascota = Mascota(
                                            nombre = nombreMascota.trim(),
                                            tipoAnimal = nombre,
                                            imagen = tipo
                                        )
                                        mascotaViewModel.agregarMascota(nuevaMascota) {
                                            isLoading = false
                                            navController.navigate("mascota") {
                                                popUpTo("EleccionAnimal") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = nombreMascota.isNotEmpty() && !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Elegir $nombre")
                            }
                        }
                    }
                }
            }

            TextButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}