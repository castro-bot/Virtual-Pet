package com.example.aplicacionmascotavirtual.viewmodel

import com.example.aplicacionmascotavirtual.models.Mascota
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class MascotaManager(
    private val mascotaSeleccionada: MutableStateFlow<Mascota?>,
    private val actualizarMascota: (Mascota) -> Unit
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        iniciarReduccionAutomatica()
    }

    private fun iniciarReduccionAutomatica() {
        scope.launch {
            while (isActive) {
                delay(5 * 60 * 1000) // 5 minutos en milisegundos
                reducirHambreYFelicidad()
            }
        }
    }

    private fun reducirHambreYFelicidad() {
        val mascotaActual = mascotaSeleccionada.value ?: return

        val mascotaActualizada = mascotaActual.copy(
            hambre = (mascotaActual.hambre - 5).coerceAtLeast(0),
            felicidad = (mascotaActual.felicidad - 5).coerceAtLeast(0)
        )

        actualizarMascota(mascotaActualizada)
        mascotaSeleccionada.value = mascotaActualizada

        println("LOG: ${mascotaActualizada.nombre} ahora tiene hambre ${mascotaActualizada.hambre} y felicidad ${mascotaActualizada.felicidad}")
    }
}
