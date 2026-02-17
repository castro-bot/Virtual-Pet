package com.example.aplicacionmascotavirtual.vistas

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.componentes.IngresarContrasenia
import com.example.aplicacionmascotavirtual.componentes.IngresarTexto
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
    var validationMessage by remember { mutableStateOf<String?>(null) }

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
                onValueChange = {
                    username = it
                    validationMessage = null
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarTexto(
                value = email,
                texto = "Correo Electronico",
                onValueChange = {
                    email = it
                    validationMessage = null
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarContrasenia(
                value = password,
                texto = "Contrasena",
                onValueChange = {
                    password = it
                    validationMessage = null
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            IngresarContrasenia(
                value = confirmPassword,
                texto = "Confirmar Contrasena",
                onValueChange = {
                    confirmPassword = it
                    validationMessage = null
                }
            )

            val displayedError = validationMessage ?: errorMessage
            if (!displayedError.isNullOrBlank()) {
                Text(
                    text = displayedError,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    validationMessage = validarCampos(username, email, password, confirmPassword)
                    if (validationMessage == null) {
                        viewModel.registrarUsuario(
                            email = email.trim(),
                            password = password,
                            nombre = username.trim()
                        ) { success ->
                            if (success) {
                                navController.navigate("EleccionAnimal") {
                                    popUpTo("registro") { inclusive = true }
                                }
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
                    "Ya tienes cuenta? Inicia sesion",
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
): String? {
    return when {
        username.isBlank() -> "Ingresa un nombre de usuario."
        email.isBlank() -> "Ingresa un correo electronico."
        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Ingresa un correo electronico valido."
        password.isBlank() -> "Ingresa una contrasena."
        password.length < 6 -> "La contrasena debe tener al menos 6 caracteres."
        password != confirmPassword -> "Las contrasenas no coinciden."
        else -> null
    }
}
