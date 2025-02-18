//package com.example.aplicacionmascotavirtual.screens
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//
//@Composable
//fun HistorialScreen(navController: NavController, historialInteracciones: List<String>) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Historial de Interacciones", fontSize = 22.sp)
//
//        // Botón para activar el menú flotante
//        Button(onClick = { expanded = !expanded }) {
//            Text("Mostrar historial")
//        }
//
//        // Menú flotante con las interacciones
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            if (historialInteracciones.isEmpty()) {
//                DropdownMenuItem(
//                    text = { Text("No hay interacciones aún") },
//                    onClick = {}
//                )
//            } else {
//                historialInteracciones.forEach { accion ->
//                    DropdownMenuItem(
//                        text = { Text(accion) },
//                        onClick = {}
//                    )
//                }
//            }
//        }
//
//        // Botón para regresar a MascotaScreen
//        Spacer(modifier = Modifier.height(20.dp))
//        Button(onClick = { navController.popBackStack() }) {
//            Text("Volver")
//        }
//    }
//}