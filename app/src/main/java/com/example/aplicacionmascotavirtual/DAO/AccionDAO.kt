package com.example.aplicacionmascotavirtual.DAO


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionmascotavirtual.models.AccionEntity

@Dao
interface AccionDao {
    @Insert
    suspend fun insertarAccion(accion: AccionEntity)

    @Query("SELECT * FROM acciones WHERE mascotaId = :mascotaId")
    suspend fun getAccionesDeMascota(mascotaId: String): List<AccionEntity>

    // Para contar cuántos eventos de un tipo lleva la mascota
    @Query("SELECT COUNT(*) FROM acciones WHERE mascotaId = :mascotaId AND tipo = :tipo")
    suspend fun contarEventos(mascotaId: String, tipo: String): Int
}

