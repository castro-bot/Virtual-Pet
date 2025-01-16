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
import com.example.aplicacionmascotavirtual.Componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.Componentes.EspacioV
import com.example.aplicacionmascotavirtual.Componentes.IngresarTexto
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.data.Mascota

@Composable
fun ConfiguracionScreen(mascota: Mascota) {
    var nombre = remember { mutableStateOf(mascota.nombre) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Configuración", fontSize = 24.sp)
                    EspacioV(20)
                    Text("Tipo de Animal: ${mascota.tipoAnimal}", fontSize = 18.sp)
                }
                EspacioV(15)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Nombre de la Mascota:", fontSize = 18.sp)
                    EspacioV(25)

                    // Aquí usamos el nombre de la imagen de la mascota para cargarla dinámicamente
                    if (mascota.imagen.isNotEmpty()) {
                        val imageRes = when (mascota.imagen) {
                            "zorro" -> R.drawable.zorro
                            "pajaro" -> R.drawable.pajaro
                            "huron" -> R.drawable.huron
                            else -> R.drawable.repuesto // Usa una imagen por defecto si no se encuentra
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(imageRes),
                                contentDescription = "Imagen de la mascota",
                                modifier = Modifier.size(200.dp)
                            )
                        }
                    }
                    EspacioV(100)
                    IngresarTexto(
                        value = nombre.value,
                        texto = "Nombre de la mascota",
                        onValueChange = { nombre.value = it })
                }
                EspacioV(25)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { mascota.nombre = nombre.value }) {
                        Text("Guardar Cambios")
                    }
                }

            }
        }
    }
}

