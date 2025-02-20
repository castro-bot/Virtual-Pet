package com.example.aplicacionmascotavirtual.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aplicacionmascotavirtual.R
import com.example.aplicacionmascotavirtual.componentes.BackgroundImage
import com.example.aplicacionmascotavirtual.componentes.LogrosComponent
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel
import kotlinx.coroutines.delay

import com.example.aplicacionmascotavirtual.componentes.LogrosComponent
import com.example.aplicacionmascotavirtual.componentes.busquedaBinaria
import com.example.aplicacionmascotavirtual.componentes.obtenerListaOrdenadaDeNecesidades
import com.example.aplicacionmascotavirtual.models.Achievement
import com.example.aplicacionmascotavirtual.vistas.DarAmorButton

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MascotaScreen(navController: NavController, context: Context) {
    val mascotaViewModel: MascotaViewModel = viewModel()
    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val mascotaSeleccionada by mascotaViewModel.mascotaSeleccionada.collectAsState()

    var mensajeMascota by remember { mutableStateOf("") }
    var mostrarMensajeLleno by remember { mutableStateOf(false) }
    val mostrarLogros by mascotaViewModel.mostrarLogros.collectAsState()

    var nivelAlimentacion by remember { mutableStateOf(0f) }

    // Historial de interacciones implementado de forma local
    val historialInteracciones = remember { mutableStateListOf<String>() }
    var mostrarHistorial by remember { mutableStateOf(false) }
    var textoBusqueda by remember { mutableStateOf("") }
    var busquedaResultado by remember { mutableStateOf("") }

    val necesidadesOrdenadas = remember(mascotaSeleccionada) {
        mascotaSeleccionada?.let { obtenerListaOrdenadaDeNecesidades(it) } ?: listOf()
    }
    // Efecto para seleccionar la primera mascota si no hay ninguna seleccionada
    LaunchedEffect(mascotas) {
        if (mascotaSeleccionada == null && mascotas.isNotEmpty()) {
            mascotaViewModel.seleccionarMascota(mascotas.first())
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()

        if (mascotas.isEmpty()) {
            // Vista cuando no hay mascotas
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.4f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "No tienes mascotas",
                    color = Color.White,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("EleccionAnimal") }) {
                    Text("Crear una mascota")
                }
            }
        } else {
            // Vista principal con mascota
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Barra superior
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("configuracion") }) {
                            Icon(Icons.Default.Settings, contentDescription = "Configuración")
                        }
                        IconButton(onClick = { navController.navigate("JuegoAdivinarVista") }) {
                            Icon(Icons.Default.Face, contentDescription = "Juego")
                        }
                        // En la barra superior, junto a los otros IconButton
                        IconButton(
                            onClick = { navController.navigate("logros") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trophy), // Necesitarás agregar este ícono
                                contentDescription = "Logros"
                            )
                        }
                        Button(onClick = { mostrarHistorial = !mostrarHistorial }) {
                            Icon(Icons.Default.List, contentDescription = "Historial")
                        }
                        Box {
                            var expandido by remember { mutableStateOf(false) }
                            Button(onClick = { expandido = true }) {
                                Text(mascotaSeleccionada?.nombre ?: "Seleccionar mascota")
                            }
                            DropdownMenu(
                                expanded = expandido,
                                onDismissRequest = { expandido = false }
                            ) {
                                mascotas.forEach { mascota ->
                                    DropdownMenuItem(
                                        onClick = {
                                            mascotaViewModel.seleccionarMascota(mascota)
                                            expandido = false
                                        },
                                        text = { Text(mascota.nombre) }
                                    )
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = { navController.navigate("EleccionAnimal") }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Crear nueva mascota")
                    }
                }

                // Contenido de la mascota seleccionada
                mascotaSeleccionada?.let { mascota ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DarAmorButton(context = context)

                        // Estadísticas
                        Text("Estado de Necesidades:", fontSize = 20.sp, color = Color.White)
                        necesidadesOrdenadas.forEach { (necesidad, cantidad) ->
                            Text("$necesidad: $cantidad%", fontSize = 18.sp, color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Imagen de la mascota
                        val imageRes = when (mascota.imagen) {
                            "zorro" -> R.drawable.zorro
                            "pajaro" -> R.drawable.pajaro
                            "huron" -> R.drawable.huron
                            else -> R.drawable.repuesto
                        }
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = mascota.tipoAnimal,
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        if (mensajeMascota.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .offset(y = (-60).dp)
                                    .background(
                                        Color.White,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .padding(10.dp)
                                    .widthIn(max = 200.dp)
                                    .zIndex(1f)
                            ) {
                                Text(
                                    text = mensajeMascota,
                                    fontSize = 16.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

// Opciones de diálogo
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text("Opciones de diálogo:", fontSize = 14.sp, color = Color.White)
                            Spacer(modifier = Modifier.height(10.dp))
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                DialogButton(
                                    "Hola",
                                    "¡Hola!",
                                    onMensajeCambio = { mensajeMascota = it })
                                DialogButton(
                                    "Chao",
                                    "¡Chao!",
                                    onMensajeCambio = { mensajeMascota = it })
                                DialogButton(
                                    "¿Cómo estás?",
                                    "Estoy bien, ¿y tú?",
                                    onMensajeCambio = { mensajeMascota = it })
                                DialogButton(
                                    "Bien",
                                    "¡Me alegra por ti!",
                                    onMensajeCambio = { mensajeMascota = it })
                            }
                        }

// Botones de interacción principales

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            IconButton(
//                                onClick = { mascotaViewModel.alimentarMascota(mascota) },
//                                modifier = Modifier.size(48.dp)
//                            ) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.comida2),
//                                    contentDescription = "Alimentar",
//                                    modifier = Modifier.size(50.dp)
//                                )
//                            }
                            IconButton(
                                onClick = {
                                    mascotaViewModel.alimentarMascota(mascota)
                                    historialInteracciones.add("Alimentó a la mascota")
                                    if (nivelAlimentacion < 1f) {
                                        nivelAlimentacion =
                                            (nivelAlimentacion + 0.1f).coerceAtMost(1f)
                                        if (nivelAlimentacion >= 1f) {
                                            mostrarMensajeLleno = true
                                        }
                                    }
                                },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.comida2),
                                    contentDescription = "Alimentar",
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(onClick = {
                                mascotaViewModel.jugarConMascota(mascota)
                                historialInteracciones.add("Jugó con la mascota")
                            }) {
                                Text("Jugar", fontSize = 16.sp)
                            }
                        }
                        if (mostrarHistorial) {
                            HistorialInteraccionesComponent(
                                historial = historialInteracciones,
                                textoBusqueda = textoBusqueda,
                                onTextoBusquedaChange = { textoBusqueda = it },
                                busquedaResultado = busquedaResultado,
                                onBuscar = {
                                    val index = busquedaBinaria(
                                        historialInteracciones.sorted(),
                                        textoBusqueda
                                    )
                                    busquedaResultado =
                                        if (index != -1) "Interacción encontrada: $textoBusqueda" else "No se encontró la interacción"
                                }
                            )
                        }

// Barra de progreso
                        // Barra de progreso sincronizada con la mascota
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val nivelTotal = mascotaViewModel.obtenerNivelTotal(mascota)
                            LinearProgressIndicator(
                                progress = nivelTotal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp),
                                color = Color.Green,
                                trackColor = Color.LightGray
                            )
                            Text(
                                text = "${(nivelTotal * 100).toInt()}%",
                                color = Color.Black
                            )
                        }

                    }
                }
            }
        }
        if (mostrarLogros) {
            AlertDialog(
                onDismissRequest = {
                    mascotaViewModel.ocultarLogros()
                },
                title = { Text("Logros") },
                text = {
                    LogrosComponent(
                        achievementManager = mascotaViewModel.getAchievementManager(),
                        navController = navController,
                        modifier = Modifier.height(400.dp)
                    )
                },
                confirmButton = {
                    TextButton(onClick = { mascotaViewModel.ocultarLogros() }) {
                        Text("Cerrar")
                    }
                }
            )
        }

    }
    if (mensajeMascota.isNotEmpty()) {
        Box(
            modifier = Modifier
                .offset(y = (-60).dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(10.dp)
                .widthIn(max = 200.dp)
                .zIndex(1f)
        ) {
            Text(
                text = mensajeMascota,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }

    // Limpiar el mensaje después de un tiempo
    LaunchedEffect(mensajeMascota) {
        if (mensajeMascota.isNotEmpty()) {
            delay(3000) // El mensaje desaparece después de 3 segundos
            mascotaViewModel.limpiarMensaje()
        }
    }

    if (mostrarMensajeLleno) {
        Text(
            "¡Estoy lleno!",
            color = Color.Green,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (mostrarHistorial) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .heightIn(min = 300.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Historial de Interacciones",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Campo de búsqueda integrado
                    var busqueda by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { busqueda = it },
                        label = { Text("Buscar en historial") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            val index = busquedaBinaria(historialInteracciones.sorted(), busqueda)
                            busquedaResultado = if (index != -1) "Interacción encontrada: $busqueda" else "No se encontró la interacción"
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Buscar")
                    }
                    Text(
                        busquedaResultado,
                        fontSize = 16.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Lista de interacciones
                    historialInteracciones.forEach { mensaje ->
                        Text(
                            text = "- $mensaje",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { mostrarHistorial = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }

}


@Composable
fun DialogButton(
    texto: String,
    mensaje: String,
    modifier: Modifier = Modifier,
    onMensajeCambio: (String) -> Unit,
) {
    Button(
        onClick = { onMensajeCambio(mensaje) },
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Text(texto)
    }
}

