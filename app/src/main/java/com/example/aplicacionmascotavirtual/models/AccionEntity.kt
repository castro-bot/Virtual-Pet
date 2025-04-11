package com.example.aplicacionmascotavirtual.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acciones")
data class AccionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mascotaId: String,
    val tipo: String // "alimentar" o "jugar"
)
