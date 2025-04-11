package com.example.aplicacionmascotavirtual.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.componentes.EspacioV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoAdivinarVista(navController: NavController) {
    var numeroAAdivinar by remember { mutableStateOf("") } // Número ingresado
    var numeroSecreto by remember { mutableStateOf((1..50).random()) } // Número a adivinar
    var intentosRestantes by remember { mutableStateOf(10) }
    var mensajeResultado by remember { mutableStateOf("") }
    var juegoTerminado by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título del juego
            Text("Adivina el número", fontSize = 40.sp)

            EspacioV(20)

            // Caja donde se muestra el número (muestra "?" hasta que se acierte)
            Box(
                modifier = Modifier
                    .background(Color.Blue, shape = RoundedCornerShape(12.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (juegoTerminado) numeroSecreto.toString() else "?",
                    fontSize = 100.sp,
                    color = Color.White
                )
            }

            EspacioV(20)

            // Mensaje de instrucciones
            Text(
                text = if (juegoTerminado) "Juego terminado, fallaste" else "Ingrese el número a adivinar (1-20)",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Text("Intentos restantes: $intentosRestantes")

            EspacioV(20)

            // Campo de entrada para el número (solo permite números)
            OutlinedTextField(
                value = numeroAAdivinar,
                onValueChange = { input ->
                    if (input.all { it.isDigit() } || input.isEmpty()) {
                        numeroAAdivinar = input
                    }
                },
                label = { Text("Número", color = Color.Black) }, // Mejor contraste
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                    .padding(5.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 20.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = VisualTransformation.None
            )

            EspacioV(20)

            // Botón para comprobar el número
            Button(
                onClick = {
                    val numeroIngresado = numeroAAdivinar.toIntOrNull()
                    if (numeroIngresado == null || numeroIngresado !in 1..20) {
                        mensajeResultado = "Por favor, ingrese un número válido entre 1 y 20."
                        return@Button
                    }

                    if (numeroIngresado == numeroSecreto) {
                        mensajeResultado = "¡Felicidades! Adivinaste el número."
                        juegoTerminado = true
                    } else {
                        intentosRestantes--
                        mensajeResultado = if (numeroIngresado < numeroSecreto) {
                            "El número es mayor. Intentos restantes: $intentosRestantes"
                        } else {
                            "El número es menor. Intentos restantes: $intentosRestantes"
                        }

                        if (intentosRestantes == 0) {
                            mensajeResultado = "Perdiste, el número era $numeroSecreto."
                            juegoTerminado = true
                        }
                    }
                },
                enabled = !juegoTerminado
            ) {
                Text("Comprobar")
            }

            EspacioV(20)

            // Mensaje de resultado con fondo semitransparente
            if (mensajeResultado.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(12.dp))
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = mensajeResultado,
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            EspacioV(20)

            // Botón para reiniciar el juego
            Button(
                onClick = {
                    numeroSecreto = (1..20).random()
                    numeroAAdivinar = ""
                    intentosRestantes = 10
                    mensajeResultado = ""
                    juegoTerminado = false
                }
            ) {
                Text("Reiniciar Juego")
            }

            EspacioV(15)

            // Botón para volver con la mascota
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver con la mascota", fontSize = 20.sp)
            }
        }
    }
}