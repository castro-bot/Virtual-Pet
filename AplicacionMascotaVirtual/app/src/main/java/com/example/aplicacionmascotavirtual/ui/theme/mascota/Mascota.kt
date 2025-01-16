package com.example.aplicacionmascotavirtual.data

data class Mascota(
    var nombre: String = "Sin Nombre",
    var hambre: Int = 50,
    var felicidad: Int = 50,
    var tipoAnimal: String = "Sin Especificar",
    var imagen: String = ""
)
