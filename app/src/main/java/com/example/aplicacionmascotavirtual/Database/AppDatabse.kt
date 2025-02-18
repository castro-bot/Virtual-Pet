package com.example.aplicacionmascotavirtual.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionmascotavirtual.DAO.AccionDao
import com.example.aplicacionmascotavirtual.DAO.AchievementDao
import com.example.aplicacionmascotavirtual.models.AccionEntity
import com.example.aplicacionmascotavirtual.models.AchievementEntity

@Database(entities = [AchievementEntity::class, AccionEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
    abstract fun accionDao(): AccionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
