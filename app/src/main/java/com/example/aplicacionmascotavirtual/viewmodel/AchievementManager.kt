package com.example.aplicacionmascotavirtual.viewmodel


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.aplicacionmascotavirtual.models.Achievement
import com.example.aplicacionmascotavirtual.models.AchievementEntity
import com.example.aplicacionmascotavirtual.Database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import com.example.aplicacionmascotavirtual.models.AccionEntity
import kotlinx.coroutines.SupervisorJob

class AchievementManager(
    private val context: Context,
    private val onLogroDesbloqueado: (() -> Unit)? = null
) {
    private val achievementDao = AppDatabase.getDatabase(context).achievementDao()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _logros = MutableStateFlow<List<Achievement>>(emptyList())
    val logros: StateFlow<List<Achievement>> = _logros

    private val accionDao = AppDatabase.getDatabase(context).accionDao()


    // Logros básicos
    val primerAlimento = Achievement(
        id = 1,
        name = "Primera Comida",
        description = "Alimentaste a tu mascota por primera vez",
        points = 10
    )

    val primerJuego = Achievement(
        id = 2,
        name = "Jugador Novato",
        description = "Jugaste con tu mascota por primera vez",
        points = 10
    )

    val cuidadorBasico = Achievement(
        id = 3,
        name = "Cuidador Básico",
        description = "Alimenta y juega con tu mascota 5 veces",
        points = 20
    )

    val masterCuidador = Achievement(
        id = 4,
        name = "Master Cuidador",
        description = "Mantén a tu mascota con felicidad y hambre por encima del 80%",
        points = 50
    )

    private val achievements = listOf(primerAlimento, primerJuego, cuidadorBasico, masterCuidador)


    private fun actualizarLogros() {
        _logros.value = achievements
    }


    fun cargarProgresoParaMascota(mascotaId: String, onLoaded: (() -> Unit)? = null) {
        scope.launch {
            val achievementEntities = achievementDao.getAchievementsByMascotaId(mascotaId)
            println("LOG: Logros encontrados en la base de datos para mascota $mascotaId: $achievementEntities")

            val updatedAchievements = achievements.map { achievement ->
                achievementEntities.find { it.id == achievement.id }?.let { entity ->
                    achievement.copy(completado = entity.completado)
                } ?: achievement
            }

            _logros.value = updatedAchievements
            println("LOG: Logros después de actualizar en StateFlow -> ${_logros.value}")
            println("LOG: cargarProgresoParaMascota - final para id=$mascotaId")

            // Notificar que la carga ha finalizado
            onLoaded?.invoke()
        }
    }


    fun guardarProgresoParaMascota(mascotaId: String) {
        scope.launch {
            _logros.value.forEach { achievement ->
                val entity = AchievementEntity(
                    id = achievement.id,
                    name = achievement.name,
                    description = achievement.description,
                    points = achievement.points,
                    completado = achievement.completado,
                    requisitosIds = "", // Ajusta si necesitas manejar requisitos
                    mascotaId = mascotaId // 🔥 Asociar logro con la mascota
                )
                achievementDao.insertAchievement(entity)
            }
            println("LOG: Logros guardados para mascota $mascotaId - ${_logros.value.map { "${it.name}: ${it.completado}" }}")
        }
    }


    fun verificarLogrosRecursivo(
        mascotaId: String,
        nivelHambre: Int,
        nivelFelicidad: Int,
        index: Int = 0
    ) {
        // 1) Lanzar corrutina en IO
        scope.launch {
            // 2) Consultar la base para saber cuántas alimentaciones / juegos lleva
            val vecesAlimentado = accionDao.contarEventos(mascotaId, "alimentar")
            val vecesJugado = accionDao.contarEventos(mascotaId, "jugar")

            // 3) Llamar a la función recursiva interna, pasándole esos valores
            verificarLogrosRecursivoInternal(
                mascotaId = mascotaId,
                nivelHambre = nivelHambre,
                nivelFelicidad = nivelFelicidad,
                vecesAlimentado = vecesAlimentado,
                vecesJugado = vecesJugado,
                index = index
            )
        }
    }

    // Esta función hace la recursión "pura"
    private fun verificarLogrosRecursivoInternal(
        mascotaId: String,
        nivelHambre: Int,
        nivelFelicidad: Int,
        vecesAlimentado: Int,
        vecesJugado: Int,
        index: Int
    ) {
        // 1) Caso base: si index >= número de logros, terminamos
        if (index >= _logros.value.size) {
            println("LOG: Se revisaron todos los logros para la mascota $mascotaId")
            guardarProgresoParaMascota(mascotaId) // Guardar en la BD achievements
            onLogroDesbloqueado?.invoke()
            return
        }

        // 2) Tomamos el logro actual
        val logro = _logros.value[index]

        // 3) Si no está completado, verificamos condición con contadores de BD
        if (!logro.completado) {
            when (logro.id) {
                1 -> if (vecesAlimentado >= 1) {
                    // Marcar completado
                    _logros.value = _logros.value.toMutableList().apply {
                        this[index] = logro.copy(completado = true)
                    }
                }
                2 -> if (vecesJugado >= 1) {
                    _logros.value = _logros.value.toMutableList().apply {
                        this[index] = logro.copy(completado = true)
                    }
                }
                3 -> if (vecesAlimentado >= 5 && vecesJugado >= 5) {
                    _logros.value = _logros.value.toMutableList().apply {
                        this[index] = logro.copy(completado = true)
                    }
                }
                4 -> if (nivelHambre >= 80 && nivelFelicidad >= 80) {
                    _logros.value = _logros.value.toMutableList().apply {
                        this[index] = logro.copy(completado = true)
                    }
                }
            }
        }

        // 4) Llamada recursiva al siguiente índice
        verificarLogrosRecursivoInternal(
            mascotaId,
            nivelHambre,
            nivelFelicidad,
            vecesAlimentado,
            vecesJugado,
            index + 1
        )
    }


    fun registrarAccion(mascotaId: String, tipo: String) {
        scope.launch {
            try {
                val nuevaAccion = AccionEntity(
                    mascotaId = mascotaId,
                    tipo = tipo
                )
                accionDao.insertarAccion(nuevaAccion)
                println("LOG: Accion insertada para mascota $mascotaId, tipo: $tipo")
            } catch (e: Exception) {
                println("LOG: ERROR al insertar accion para mascota $mascotaId, tipo: $tipo, error: ${e.message}")
            }
        }
    }


    fun obtenerLogrosDisponibles(): List<Achievement> = achievements
    fun obtenerPuntosTotales(): Int = achievements.filter { it.completado }.sumOf { it.points }
}
