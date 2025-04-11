package com.example.aplicacionmascotavirtual.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionmascotavirtual.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun login(email: String, password: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                auth.signInWithEmailAndPassword(email, password).await()
                onComplete(true)
            } catch (e: Exception) {
                _errorMessage.value = "Error al iniciar sesión: ${e.localizedMessage}"
                onComplete(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun registrarUsuario(email: String, password: String, nombre: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = authResult.user?.uid

                if (userId != null) {
                    val nuevoUsuario = User(
                        id = userId,
                        name = nombre,
                        email = email
                    )
                    firestore.collection("usuarios")
                        .document(userId)
                        .set(nuevoUsuario)
                        .await()

                    onComplete(true)
                } else {
                    onComplete(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al registrar usuario: ${e.localizedMessage}"
                onComplete(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkUsuarioLoggeado(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun cerrarSesion() {
        auth.signOut()
    }

    fun limpiarError() {
        _errorMessage.value = null
    }
}