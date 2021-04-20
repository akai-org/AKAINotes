package pl.kossa.akainotes.fragments.viewNote

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

object NoteViewViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")

    fun getTitle(): String {
        return _title.value.toString()
    }

    fun setTitle(value: String) {
        _title.value = value
    }

    fun getContent(): String {
        return _content.value.toString()
    }

    fun setContent(value: String) {
        _content.value = value
    }

}
