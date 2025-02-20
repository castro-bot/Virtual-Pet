package com.example.aplicacionmascotavirtual.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.componentes.busquedaBinaria
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel

@Composable
fun HistorialInteraccionesComponent(
    historial: List<String>,
    textoBusqueda: String,
    onTextoBusquedaChange: (String) -> Unit,
    busquedaResultado: String,
    onBuscar: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Historial de Interacciones:", fontSize = 20.sp, color = Color.White)
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = onTextoBusquedaChange,
            label = { Text("Buscar en historial") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Button(onClick = onBuscar) {
            Text("Buscar")
        }
        Text(
            busquedaResultado,
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.padding(10.dp)
        )
        historial.forEach { accion ->
            Text("- $accion", fontSize = 16.sp, color = Color.White)
        }
    }
}
