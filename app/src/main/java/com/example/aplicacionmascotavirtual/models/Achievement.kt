package com.example.aplicacionmascotavirtual.models

data class Achievement(
    val id: Int,
    val name: String,
    val description: String,
    val points: Int,
    var completado: Boolean = false,
)