package com.example.aplicacionmascotavirtual.models

data class Mascota(
    val id: String = "",
    var nombre: String = "",
    val usuarioId: String = "",
    var hambre: Int = 50,
    var felicidad: Int = 50,
    var tipoAnimal: String = "",
    var imagen: String = ""
)