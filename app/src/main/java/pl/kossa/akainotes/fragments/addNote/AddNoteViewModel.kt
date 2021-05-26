package pl.kossa.akainotes.fragments.addNote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kossa.akainotes.api.RetrofitClient
import pl.kossa.akainotes.data.Note
import pl.kossa.akainotes.prefs.PrefsHelper

class AddNoteViewModel(prefsHelper: PrefsHelper) : ViewModel() {
    private val retrofitClient = RetrofitClient(prefsHelper)

    val _title = MutableStateFlow("")
    val _content = MutableStateFlow("")
    val noteAdded = MutableLiveData<Boolean>()
    val loading = MutableLiveData(false)

    val isSaveNoteEnabled = combine(_title, _content) { title, content ->
        return@combine title.isNotBlank() && title.length <= 100 && content.length <= 500
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    retrofitClient.addNote(note)
                }
                noteAdded.value = true
            } catch (e: Exception) {
                Log.e("AddNoteViewModel", e.message, e)
            }
            loading.value = false
        }
    }

    fun getTitle(): String {
        return _title.value
    }

    fun setTitle(value: String) {
        _title.value = value
    }

    fun getContent(): String {
        return _content.value
    }

    fun setContent(value: String) {
        _content.value = value
    }
}