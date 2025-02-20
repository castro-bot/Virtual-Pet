package com.example.aplicacionmascotavirtual.vistas

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionmascotavirtual.R
import kotlinx.coroutines.delay

fun guardarUltimaHora(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MascotaPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putLong("ultimaHora", System.currentTimeMillis()).apply()
}

fun obtenerUltimaHora(context: Context): Long {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MascotaPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getLong("ultimaHora", System.currentTimeMillis())
}

@Composable
fun DarAmorButton(context: Context, modifier: Modifier = Modifier) {
    var corazones by remember { mutableStateOf(4) }
    var corazonesMedios by remember { mutableStateOf(0) }

    // Lógica de recarga de corazones cada 2 minutos
    LaunchedEffect(Unit) {
        while (true) {
            delay(2 * 60 * 1000)
            if (corazonesMedios > 0) {
                corazonesMedios--
            } else if (corazones > 0) {
                corazones--
                corazonesMedios++
            }
            guardarUltimaHora(context)
        }
    }

    // Un solo contenedor (Row) para íconos + botón
    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        // Corazones
        Row {
            // Ajusta la cantidad o tamaño de los corazones según quieras
            repeat(corazones) {
                Icon(
                    painter = painterResource(id = R.drawable.corazonlleno1),
                    contentDescription = "Corazón lleno",
                    modifier = Modifier.size(24.dp), // reduce de 32dp a 24dp
                    tint = Color.Red
                )
            }
            repeat(corazonesMedios) {
                Icon(
                    painter = painterResource(id = R.drawable.corazonmediolleno1),
                    contentDescription = "Corazón medio",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
            repeat(4 - corazones - corazonesMedios) {
                Icon(
                    painter = painterResource(id = R.drawable.corazonvacio1),
                    contentDescription = "Corazón vacío",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Botón de "Dar Amor"
        OutlinedButton(
            onClick = {
                if (corazonesMedios > 0) {
                    corazonesMedios--
                    corazones++
                } else if (corazones < 4) {
                    corazones++
                }
                guardarUltimaHora(context)
            },
            enabled = (corazones < 4 || corazonesMedios > 0),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier // Ajusta tamaño o elimina si prefieres
                .height(36.dp)   // reduce la altura del botón
                .padding(horizontal = 4.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.corazonlleno1),
                contentDescription = "Dar amor",
                modifier = Modifier.size(16.dp), // ícono más pequeño
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Dar Amor",
                fontSize = 14.sp,
                color = Color.Black// reduce de 18.sp
            )
        }
    }
}
