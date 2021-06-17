package pl.kossa.akainotes.fragments.note

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pl.kossa.akainotes.api.RetrofitClient
import pl.kossa.akainotes.data.Note
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val retrofitClient: RetrofitClient
) : ViewModel() {

    val isLoading = MutableStateFlow(false)
    val note = MutableStateFlow<Note?>(null)
    private val noteId = savedStateHandle.get<String>("noteId")!!

    init {
        getNote()
    }

    fun getNote() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = retrofitClient.getNote(noteId)
                Log.d("MyLog","Note Response")
                delay(500)
                note.value = response
            } catch (e: Exception) {
                Log.e("MyLog", "Exception: $e")
            } finally {
                isLoading.value = false
            }
        }
    }
}