package pl.kossa.akainotes.api

import pl.kossa.akainotes.api.models.LoginRequest
import pl.kossa.akainotes.api.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface NotesApi {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse
}