package com.example.aplicacionmascotavirtual.vistas

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
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.componentes.EspacioV
import com.example.aplicacionmascotavirtual.componentes.IngresarContrasenia
import com.example.aplicacionmascotavirtual.componentes.IngresarTexto
import com.example.aplicacionmascotavirtual.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InicioSesion(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logomascota2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 40.dp)
            )

            Text(
                text = "INICIAR SESIÓN",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            IngresarTexto(
                value = email,
                texto = "Correo Electrónico",
                onValueChange = { email = it }
            )

            EspacioV(16)

            IngresarContrasenia(
                value = password,
                texto = "Contraseña",
                onValueChange = { password = it }
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            EspacioV(32)
            Button(
                onClick = {
                    isLoading = true
                    viewModel.login(email, password) { success ->
                        isLoading = false
                        if (success) {
                            navController.navigate("mascota") {
                                popUpTo("iniciarsesion") { inclusive = true }
                            }
                        } else {
                            errorMessage = "Error al iniciar sesión. Verifica tus credenciales."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("INICIAR SESIÓN", fontSize = 18.sp)
                }
            }

            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿No tienes cuenta? ", color = Color.White)
                TextButton(onClick = { navController.navigate("registro") }) {
                    Text("Regístrate", color = Color.Cyan)
                }
            }
        }
    }
}
