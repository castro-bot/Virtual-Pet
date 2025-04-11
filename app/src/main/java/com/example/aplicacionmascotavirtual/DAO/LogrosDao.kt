package com.example.aplicacionmascotavirtual.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.aplicacionmascotavirtual.models.AchievementEntity

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    suspend fun getAllAchievements(): List<AchievementEntity>

    @Query("SELECT * FROM achievements WHERE id = :achievementId")
    suspend fun getAchievementById(achievementId: Int): AchievementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: AchievementEntity)

    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)

    @Query("UPDATE achievements SET completado = :completed WHERE id = :achievementId")
    suspend fun updateAchievementCompletion(achievementId: Int, completed: Boolean)

    @Query("SELECT * FROM achievements WHERE mascotaId = :mascotaId")
    suspend fun getAchievementsByMascotaId(mascotaId: String): List<AchievementEntity>

    @Query("UPDATE achievements SET completado = :completed WHERE mascotaId = :mascotaId AND id = :achievementId")
    suspend fun updateAchievementForMascota(mascotaId: String, achievementId: Int, completed: Boolean)
}