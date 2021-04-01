package pl.kossa.akainotes.fragments.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val isLoginEnabled = combine(_email, _password) { email, password ->
        return@combine password.isNotBlank() && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }


    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun getEmail(): String {
        return _email.value
    }

    fun getPassword(): String {
        return _password.value
    }
}