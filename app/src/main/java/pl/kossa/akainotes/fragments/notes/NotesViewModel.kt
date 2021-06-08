package pl.kossa.akainotes.fragments.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.kossa.akainotes.api.RetrofitClient
import pl.kossa.akainotes.api.models.LoginRequest
import pl.kossa.akainotes.data.Note
import pl.kossa.akainotes.fragments.login.LoginFragmentDirections
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val retrofitClient: RetrofitClient
) : ViewModel() {

    companion object {
        private const val TAG = "NotesViewModel_"
    }

    val isLoading = MutableStateFlow(false)
    val notesList = MutableStateFlow(listOf<Note>())

    init {
        getNotes()
        Log.d(TAG, "RetrofitClient hash: ${retrofitClient.hashCode()}")
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = retrofitClient.getNotes()
                delay(500)
                notesList.value = response
            } catch (e: Exception) {
                Log.e("MyLog", "Exception: $e")
            } finally {
                isLoading.value = false
            }
        }
    }
}