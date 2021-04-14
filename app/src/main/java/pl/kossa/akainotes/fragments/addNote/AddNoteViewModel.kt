package pl.kossa.akainotes.fragments.addNote

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class AddNoteViewModel : ViewModel() {
    val _title = MutableStateFlow("")
    val _content = MutableStateFlow("")

    val isSaveNoteEnabled = combine(_title, _content) {title, content ->
        return@combine title.isNotBlank() && title.length <= 100 && content.length <= 500
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