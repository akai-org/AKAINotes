package pl.kossa.akainotes.fragments.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.kossa.akainotes.fragments.notes.NotesViewModel
import pl.kossa.akainotes.prefs.PrefsHelper
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel_"
    }

    init {
        Log.d(TAG, "PrefsHelper hash: ${prefsHelper.hashCode()}")
    }

    private val _email = MutableStateFlow(prefsHelper.token?: "")
    val email: StateFlow<String>
        get() = _email
    private val _userName = MutableStateFlow("user name")
    val userName: StateFlow<String>
        get() = _userName

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

}