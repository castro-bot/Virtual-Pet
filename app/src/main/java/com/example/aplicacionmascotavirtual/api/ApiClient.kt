import com.example.aplicacionmascotavirtual.models.User
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}

class ApiClient {
    private val client = OkHttpClient()
    private val baseUrl = "https://788c-2800-bf0-a008-1029-b941-d1ce-e3bc-5ddd.ngrok-free.app"

    suspend fun registerUser(user: User): ApiResult<String> {
        return suspendCoroutine { continuation ->
            try {
                val json = JSONObject().apply {
                    put("name", user.name)
                    put("email", user.email)
                    put("psw", user.psw)  // Cambiado a psw
                }

                val requestBody = json.toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaType())

                val request = Request.Builder()
                    .url("$baseUrl/users/")
                    .post(requestBody)
                    .header("Content-Type", "application/json")
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resume(ApiResult.Error(e.message ?: "Error de conexión"))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        response.use {
                            if (response.isSuccessful) {
                                continuation.resume(ApiResult.Success(
                                    response.body?.string() ?: "Registro exitoso"
                                ))
                            } else {
                                val errorBody = response.body?.string()
                                continuation.resume(ApiResult.Error(
                                    "Error ${response.code}: $errorBody"
                                ))
                            }
                        }
                    }
                })
            } catch (e: Exception) {
                continuation.resume(ApiResult.Error(e.message ?: "Error desconocido"))
            }
        }
    }
}