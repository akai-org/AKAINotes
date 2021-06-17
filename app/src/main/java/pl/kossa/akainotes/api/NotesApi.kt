package pl.kossa.akainotes.api

import pl.kossa.akainotes.api.models.LoginRequest
import pl.kossa.akainotes.api.models.TokenResponse
import pl.kossa.akainotes.data.Note
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotesApi {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): TokenResponse

    @POST("/notes")
    suspend fun addNote(@Body note: Note)

    @GET("/notes")
    suspend fun getNotes(): List<Note>

    @GET("/notes/{noteId}")
    suspend fun getNote(@Path("noteId") noteId: String): Note
}