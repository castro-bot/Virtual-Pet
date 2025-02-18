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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.componentes.*
import com.example.aplicacionmascotavirtual.viewmodel.AuthViewModel

@Composable
fun Registro(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logomascota2),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 32.dp)
            )

            Text(
                "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            IngresarTexto(
                value = username,
                texto = "Nombre de Usuario",
                onValueChange = { username = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarTexto(
                value = email,
                texto = "Correo Electrónico",
                onValueChange = { email = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarContrasenia(
                value = password,
                texto = "Contraseña",
                onValueChange = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarContrasenia(
                value = confirmPassword,
                texto = "Confirmar Contraseña",
                onValueChange = { confirmPassword = it }
            )

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (validarCampos(username, email, password, confirmPassword)) {
                        viewModel.registrarUsuario(
                            email = email,
                            password = password,
                            nombre = username
                        ) {
                            navController.navigate("EleccionAnimal") {
                                popUpTo("registro") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("REGISTRARSE", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigateUp() }
            ) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión",
                    color = Color.White
                )
            }
        }
    }
}

private fun validarCampos(
    username: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return when {
        username.isEmpty() -> false
        email.isEmpty() -> false
        password.isEmpty() -> false
        password != confirmPassword -> false
        password.length < 6 -> false
        else -> true
    }
}