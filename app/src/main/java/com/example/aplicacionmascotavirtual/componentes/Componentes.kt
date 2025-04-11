package com.example.aplicacionmascotavirtual.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aplicacionmascotavirtual.R

@Composable
fun EspacioV(i: Int){
    Spacer(modifier = Modifier.height(i.dp))
}

@Composable
fun BackgroundImage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val image: Painter = painterResource(id = R.drawable.granja2)  // Asegúrate de que esta imagen esté en la carpeta drawable
        Image(
            painter = image,
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,  // Ajusta la imagen para cubrir la pantalla
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresarTexto(
    value: String,texto: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(texto) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Cyan,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Cyan,
            focusedLabelColor = Color.Cyan,
            unfocusedLabelColor = Color.LightGray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresarContrasenia(
    value: String,
    texto: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(texto) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Cyan,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Cyan,
            focusedLabelColor = Color.Cyan,
            unfocusedLabelColor = Color.LightGray
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}
