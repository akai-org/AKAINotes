package pl.kossa.akainotes.api

import android.util.Log
import okhttp3.OkHttpClient
import pl.kossa.akainotes.api.models.LoginRequest
import pl.kossa.akainotes.data.Note
import pl.kossa.akainotes.prefs.PrefsHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(
    val prefsHelper: PrefsHelper
) {

    private val client = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
            val path = request.url().pathSegments().joinToString(separator = "/")
            return@addInterceptor if (path != "login") {
                val newRequest = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${prefsHelper.token}")
                    .build()
                it.proceed(newRequest)
            } else {
                it.proceed(request)
            }
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serivce = retrofit.create(NotesApi::class.java)

    suspend fun login(loginRequest: LoginRequest) = serivce.login(loginRequest)

    suspend fun addNote(note: Note) = serivce.addNote(note)
}