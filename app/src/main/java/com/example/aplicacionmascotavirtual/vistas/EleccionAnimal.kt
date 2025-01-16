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
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.data.Mascota

@Composable
fun EleccionAnimalScreen(mascota: Mascota, onAnimalSeleccionado: (String) -> Unit) {
    var selectedAnimal by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Eleccion de especie de mascota", fontSize = 25.sp)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.zorro), contentDescription = "Zorro",
                    modifier = Modifier.size(100.dp)
                )
                EspacioV(10)
                Button(onClick = {
                    selectedAnimal = "zorro"
                    onAnimalSeleccionado(selectedAnimal ?: "")
                    mascota.imagen = selectedAnimal ?: ""
                }) {
                    Text("Zorro")
                }
                EspacioV(20)
                Image(
                    painter = painterResource(R.drawable.pajaro), contentDescription = "Pájaro",
                    modifier = Modifier.size(100.dp)
                )
                EspacioV(10)
                Button(onClick = {
                    selectedAnimal = "pajaro"
                    onAnimalSeleccionado(selectedAnimal ?: "")
                    mascota.imagen = selectedAnimal ?: ""
                }) {
                    Text("pajaro")
                }
                EspacioV(20)
                Image(
                    painter = painterResource(R.drawable.huron), contentDescription = "Huron",
                    modifier = Modifier.size(100.dp)
                )
                EspacioV(10)
                Button(onClick = {
                    selectedAnimal = "huron"
                    onAnimalSeleccionado(selectedAnimal ?: "")
                    mascota.imagen = selectedAnimal ?: ""
                }) {
                    Text("Hurón")
                }
            }

        }
    }

}
