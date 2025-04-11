package com.example.aplicacionmascotavirtual.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val points: Int,
    var completado: Boolean = false,
    val requisitosIds: String,
    val mascotaId: String
)
