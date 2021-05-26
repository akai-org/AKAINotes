package pl.kossa.akainotes.data

import com.google.gson.annotations.SerializedName

data class Note(
    val title: String?,
    @SerializedName("note")
    val description: String
)