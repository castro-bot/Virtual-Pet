package com.example.aplicacionmascotavirtual

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionmascotavirtual.ui.theme.AplicacionMascotaVirtualTheme
import com.example.aplicacionmascotavirtual.navegacion.AppNavigation
import com.example.aplicacionmascotavirtual.models.Mascota
import com.example.aplicacionmascotavirtual.viewmodel.MascotaViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            AplicacionMascotaVirtualTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}