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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage() // Fondo de pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f)),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp), // Para asegurar espacio desde el borde superior
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo de la aplicación
                Image(
                    painter = painterResource(R.drawable.logomascota2),
                    contentDescription = "Texto",
                    modifier = Modifier.size(250.dp) // Ajuste del tamaño del logo
                )
            }

            Spacer(modifier = Modifier.height(30.dp)) // Espacio entre el logo y el formulario

            // Campos de texto para el nombre y la contraseña
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //version actualizada en componente del outliner
                IngresarTexto(
                    value = username,
                    texto = "Ingrese Usuario",
                    onValueChange = { username = it })
                EspacioV(16)

                IngresarTexto(
                    value = email,
                    texto = "Ingrese Email",
                    onValueChange = { email = it })
                EspacioV(16)

                IngresarContrasenia(
                    value = password,
                    texto = "Ingrese contraseña",
                    onValueChange = { password = it })

                Spacer(modifier = Modifier.height(10.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


                Spacer(modifier = Modifier.height(20.dp))

                // Botón de inicio de sesión
                Button(onClick = {
                    if (username == "a" && password == "b" && email == "c") {
                        navController.navigate("eleccion_animal")  // Navegar a la siguiente pantalla
                    } else {
                        errorMessage = "Usuario o contraseña incorrectos"
                    }
                }) {
                    Text("Iniciar sesión", fontSize = 20.sp)
                }
            }
        }
    }
}



