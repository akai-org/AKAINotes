package pl.kossa.akainotes.fragments

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class RegisterViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeatPassword = MutableStateFlow("")

    val isChangePasswordEnabled = combine(_email, _password, _repeatPassword) { email, password, repeatPassword ->
        return@combine email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank() && (password == repeatPassword) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun setOldPassword(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setRepeatPassword(password: String) {
        _repeatPassword.value = password
    }

    fun getEmail(): String {
        return _email.value
    }

    fun getPassowrd(): String {
        return _password.value
    }

    fun getRepetPassowrd(): String {
        return _repeatPassword.value
    }
}
