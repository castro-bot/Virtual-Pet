package com.example.aplicacionmascotavirtual.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel
import com.example.aplicacionmascotavirtual.models.Achievement
import com.example.aplicacionmascotavirtual.viewmodel.AchievementManager
import com.example.aplicacionmascotavirtual.componentes.LogrosComponent

@Composable
fun LogrosScreen(achievementManager: AchievementManager, navController: NavController) {
    val logros by achievementManager.logros.collectAsState()

    LaunchedEffect(Unit) {
        println("LOG: Cargando LogrosScreen con ${logros.size} logros")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Logros",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White
            )

            Text(
                "Puntos totales: ${logros.filter { it.completado }.sumOf { it.points }}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(logros) { logro ->
                    println("LOG: Mostrando logro en UI - ${logro.name}, completado: ${logro.completado}")
                    LogroItem(logro)
                }
            }

            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Volver")
            }
        }
    }
}



@Composable
private fun LogroItem(logro: Achievement) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (logro.completado) {
                Color(0xFF4CAF50).copy(alpha = 0.7f)
            } else {
                Color.Gray.copy(alpha = 0.7f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = logro.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = logro.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Puntos: ${logro.points}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}