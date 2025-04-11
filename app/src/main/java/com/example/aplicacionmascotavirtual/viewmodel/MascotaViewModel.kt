package com.example.aplicacionmascotavirtual.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.aplicacionmascotavirtual.models.Mascota
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MascotaViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas: StateFlow<List<Mascota>> = _mascotas

    private val _mascotaSeleccionada = MutableStateFlow<Mascota?>(null)
    val mascotaSeleccionada: StateFlow<Mascota?> = _mascotaSeleccionada

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mostrarLogros = MutableStateFlow(false)
    val mostrarLogros: StateFlow<Boolean> = _mostrarLogros

    private val scope = CoroutineScope(Dispatchers.IO)

    private val achievementManager = AchievementManager(application) {

    }

    init {
        println("LOG: MascotaViewModel inicializado")
        cargarMascotas()
        cargarUltimaMascotaSeleccionada()
    }


    fun cargarMascotas() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("mascotas")
            .whereEqualTo("usuarioId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _error.value = "Error al cargar mascotas: ${e.message}"
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val mascotasList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Mascota::class.java)?.copy(id = doc.id)
                    }
                    _mascotas.value = mascotasList
                }
            }
    }

    suspend fun existeNombreMascota(nombre: String): Boolean {
        val userId = auth.currentUser?.uid ?: return false
        val snapshot = firestore.collection("mascotas")
            .whereEqualTo("usuarioId", userId)
            .whereEqualTo("nombre", nombre)
            .get()
            .await()
        return !snapshot.isEmpty
    }

    // En MascotaViewModel.kt
    suspend fun agregarMascota(mascota: Mascota, onSuccess: () -> Unit) {
        try {
            val userId = auth.currentUser?.uid ?: return

            if (existeNombreMascota(mascota.nombre)) {
                _error.value = "Ya tienes una mascota con ese nombre"
                return
            }

            val nuevaMascota = mascota.copy(
                id = firestore.collection("mascotas").document().id,
                usuarioId = userId
            )

            firestore.collection("mascotas")
                .document(nuevaMascota.id)
                .set(nuevaMascota)
                .await()

            // Seleccionar la nueva mascota automáticamente
            _mascotaSeleccionada.value = nuevaMascota
            // Guardar como última mascota seleccionada
            firestore.collection("usuarios")
                .document(userId)
                .update("ultimaMascotaId", nuevaMascota.id)

            cargarMascotas()
            _error.value = null
            onSuccess()
        } catch (e: Exception) {
            _error.value = "Error al crear mascota: ${e.message}"
        }
    }

    fun actualizarMascota(mascota: Mascota) {
        firestore.collection("mascotas")
            .document(mascota.id)
            .set(mascota)
    }

    fun seleccionarMascota(mascota: Mascota) {
        println("LOG: seleccionando mascota con id=${mascota.id} y nombre=${mascota.nombre}")

        _mascotaSeleccionada.value = mascota
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("usuarios")
            .document(userId)
            .update("ultimaMascotaId", mascota.id)

        // Cargar los logros solo para la mascota seleccionada
        achievementManager.cargarProgresoParaMascota(mascota.id) {
            println("LOG: terminar de cargar logros para id=${mascota.id}")
        }
    }


    fun actualizarEstadoMascota(hambre: Int, felicidad: Int) {
        val mascotaActual = _mascotaSeleccionada.value ?: return
        val mascotaActualizada = mascotaActual.copy(
            hambre = hambre,
            felicidad = felicidad
        )
        actualizarMascota(mascotaActualizada)
    }

    fun limpiarError() {
        _error.value = null
    }

    // En MascotaViewModel.kt - Agregar estado para mensaje de logro
    private val _mensajeMascota = MutableStateFlow<String>("")
    val mensajeMascota: StateFlow<String> = _mensajeMascota

    fun alimentarMascota(mascota: Mascota) {
        if (mascota.hambre < 100) {
            val mascotaActualizada = mascota.copy(
                hambre = (mascota.hambre + 10).coerceAtMost(100)
            )
            actualizarMascota(mascotaActualizada)
            _mascotaSeleccionada.value = mascotaActualizada

            println("LOG: Alimentando a ${mascota.nombre}, nueva hambre: ${mascotaActualizada.hambre}")

            // Insertar acción en la tabla
            achievementManager.registrarAccion(mascotaActualizada.id, "alimentar")

            // Llamar a la verificación recursiva
            achievementManager.verificarLogrosRecursivo(
                mascotaActualizada.id,
                mascotaActualizada.hambre,
                mascotaActualizada.felicidad
            )
        }
    }

    fun jugarConMascota(mascota: Mascota) {
        if (mascota.felicidad < 100) {
            val mascotaActualizada = mascota.copy(
                felicidad = (mascota.felicidad + 10).coerceAtMost(100)
            )
            actualizarMascota(mascotaActualizada)
            _mascotaSeleccionada.value = mascotaActualizada

            println("LOG: Jugando con ${mascota.nombre}, nueva felicidad: ${mascotaActualizada.felicidad}")

            // Insertar acción en la tabla
            achievementManager.registrarAccion(mascotaActualizada.id, "jugar")

            // Llamar a la verificación recursiva
            achievementManager.verificarLogrosRecursivo(
                mascotaActualizada.id,
                mascotaActualizada.hambre,
                mascotaActualizada.felicidad
            )
        }
    }




    fun limpiarMensaje() {
        _mensajeMascota.value = ""
    }

    fun obtenerNivelTotal(mascota: Mascota): Float {
        return (mascota.hambre + mascota.felicidad) / 200f
    }

    fun mostrarLogros() {
        _mostrarLogros.value = true
    }

    fun ocultarLogros() {
        _mostrarLogros.value = false
    }

    private fun cargarUltimaMascotaSeleccionada() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("usuarios")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val ultimaMascotaId = document.getString("ultimaMascotaId")
                if (ultimaMascotaId != null) {
                    firestore.collection("mascotas")
                        .document(ultimaMascotaId)
                        .get()
                        .addOnSuccessListener { mascotaDoc ->
                            mascotaDoc.toObject(Mascota::class.java)?.let { mascota ->
                                _mascotaSeleccionada.value = mascota.copy(id = mascotaDoc.id)

                                // ⚠️ Ahora sí cargamos los logros de esta mascota
                                achievementManager.cargarProgresoParaMascota(mascota.id) {

                                }
                            }
                        }
                }
            }
    }

    // 🔹 Reducción automática de hambre y felicidad cada 5 minutos
    private fun iniciarReduccionAutomatica() {
        scope.launch {
            while (isActive) {
                delay(5 * 60 * 1000) // 5 minutos en milisegundos
                reducirHambreYFelicidad()
            }
        }
    }

    // 🔹 Reduce el hambre y la felicidad de la mascota
    private fun reducirHambreYFelicidad() {
        val mascotaActual = _mascotaSeleccionada.value ?: return

        val mascotaActualizada = mascotaActual.copy(
            hambre = (mascotaActual.hambre - 5).coerceAtLeast(0),
            felicidad = (mascotaActual.felicidad - 5).coerceAtLeast(0)
        )
    }

    fun getAchievementManager() = achievementManager
}