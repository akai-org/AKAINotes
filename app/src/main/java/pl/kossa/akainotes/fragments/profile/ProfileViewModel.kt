package pl.kossa.akainotes.fragments.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.kossa.akainotes.prefs.PrefsHelper

class ProfileViewModel(
    private val prefsHelper: PrefsHelper
) : ViewModel() {

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