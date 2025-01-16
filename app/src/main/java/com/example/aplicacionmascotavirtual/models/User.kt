package com.example.aplicacionmascotavirtual.models
data class User(
    val name: String,
    val email: String,
    val psw: String  // Cambiado de password a psw para coincidir con el servidor
)