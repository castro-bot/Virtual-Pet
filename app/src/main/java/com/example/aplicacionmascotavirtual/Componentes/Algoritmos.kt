package com.example.aplicacionmascotavirtual.componentes

import com.example.aplicacionmascotavirtual.models.Mascota


fun busquedaBinaria(historial: List<String>, objetivo: String): Int {
    var inicio = 0
    var fin = historial.size - 1
    while (inicio <= fin) {
        val medio = inicio + (fin - inicio) / 2
        when {
            historial[medio] == objetivo -> return medio
            historial[medio] < objetivo -> inicio = medio + 1
            else -> fin = medio - 1
        }
    }
    return -1
}


fun ordenarNecesidades(necesidades: MutableList<Pair<String, Int>>): MutableList<Pair<String, Int>> {
    return necesidades.sortedBy { it.second }.toMutableList()
}


fun obtenerListaOrdenadaDeNecesidades(mascota: Mascota): List<Pair<String, Int>> {
    val necesidades = mutableListOf(
        "Hambre" to mascota.hambre,
        "Felicidad" to mascota.felicidad
    )
    return ordenarNecesidades(necesidades)
}

/**
 * Análisis de Complejidad (O, Ω, Θ)
 * - Búsqueda Binaria: O(log n) en el peor caso, Ω(1) en el mejor caso, Θ(log n) en promedio.
 * - Ordenación por cantidad: O(n log n) en promedio.
 * - Cola de Prioridad: O(log n) para inserción y extracción.
 * - Árbol de Estados: Búsqueda O(n) en el peor caso si es un árbol no balanceado.
 */
