package pl.kossa.akainotes.fragments.addNote

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddNoteViewModel : ViewModel() {
    val title = MutableStateFlow("")
    val content = MutableStateFlow("")

    fun getTitle(): String {
        return title.value
    }

    fun setTitle(value: String) {
        title.value = value
    }

    fun getContent(): String {
        return content.value
    }

    fun setContent(value: String) {
        content.value = value
    }
}