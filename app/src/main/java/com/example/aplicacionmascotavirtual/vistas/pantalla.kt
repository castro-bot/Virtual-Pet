package com.example.aplicacionmascotavirtual.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.Componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.Componentes.EspacioV
import com.example.aplicacionmascotavirtual.Componentes.IngresarContrasenia
import com.example.aplicacionmascotavirtual.Componentes.IngresarTexto
import com.example.aplicacionmascotavirtual.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicio(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(R.drawable.logomascota2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 40.dp)
            )

            // Título "INICIAR SESIÓN"
            Text(
                text = "INICIAR SESIÓN",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Texto secundario
            Text(
                text = "con tu cuenta de Mascota Virtual™",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de usuario/email
            IngresarTexto(
                value = username,
                texto = "Usuario o Email",
                onValueChange = { username = it }
            )

            EspacioV(16)

            // Campo de contraseña
            IngresarContrasenia(
                value = password,
                texto = "Contraseña",
                onValueChange = { password = it }
            )

            // Mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            EspacioV(32)

            // Botón de iniciar sesión
            Button(
                onClick = { /* Manejar inicio de sesión */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("INICIAR SESIÓN", fontSize = 18.sp)
            }

            // Link de "¿Olvidaste tu contraseña?"
            TextButton(
                onClick = { /* Manejar olvido de contraseña */ }
            ) {
                Text(
                    "¿Olvidaste tu contraseña?",
                    color = Color.White
                )
            }

            // Link de crear cuenta
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "o ",
                    color = Color.White
                )
                TextButton(
                    onClick = { navController.navigate("registro") }
                ) {
                    Text(
                        "crear una cuenta",
                        color = Color.Cyan
                    )
                }
            }
        }
    }
}