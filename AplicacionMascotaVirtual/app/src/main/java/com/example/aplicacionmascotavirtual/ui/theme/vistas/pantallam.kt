package com.example.aplicacionmascotavirtual.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.data.Mascota

@Composable
fun MascotaScreen(mascota: Mascota) {
    var isVisible by remember { mutableStateOf(true) }
    var nivelAlimentacion by remember { mutableStateOf(0f) }
    var mostrarMensajeLleno by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mascota: ${mascota.nombre}", fontSize = 24.sp)
        Text("Tipo de Animal: ${mascota.tipoAnimal}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Hambre: ${mascota.hambre}", fontSize = 18.sp)
        Text("Felicidad: ${mascota.felicidad}", fontSize = 18.sp)

        // Barra de alimentación
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                progress = nivelAlimentacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color.Green,
                trackColor = Color.LightGray
            )
            Text(
                text = "${(nivelAlimentacion * 100).toInt()}%",
                color = Color.Black
            )
        }

        // Mensaje cuando está lleno
        if (mostrarMensajeLleno) {
            Text(
                "¡Estoy lleno!",
                color = Color.Green,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Imagen de la mascota
        val animalImageRes = when (mascota.tipoAnimal) {
            "Zorro" -> R.drawable.zorro
            "Pájaro" -> R.drawable.pajaro
            "Hurón" -> R.drawable.huron
            else -> R.drawable.zorro
        }

        Image(
            painter = painterResource(id = animalImageRes),
            contentDescription = "${mascota.tipoAnimal} Mascota",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de alimentar con icono
        IconButton(
            onClick = {
                if (nivelAlimentacion < 1f) {
                    nivelAlimentacion = (nivelAlimentacion + 0.1f).coerceAtMost(1f)
                    mascota.hambre -= 10
                    if (nivelAlimentacion >= 1f) {
                        mostrarMensajeLleno = true
                    }
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.comida),
                contentDescription = "Alimentar",
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            mascota.felicidad += 10
            isVisible = !isVisible
        }) {
            Text("Jugar")
        }
    }
}

