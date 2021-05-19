package pl.kossa.akainotes.api.models

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("jwt")
    val token: String
)
