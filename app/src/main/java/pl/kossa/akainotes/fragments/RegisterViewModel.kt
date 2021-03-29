package pl.kossa.akainotes.fragments

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class RegisterViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _repeatPassword = MutableStateFlow("")
    private val _isCheckedAcceptTerms = MutableStateFlow(false)

    val isChangePasswordEnabled = combine(_email, _password, _repeatPassword, _isCheckedAcceptTerms) { email, password, repeatPassword, isChecked ->
        return@combine email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank() && (password == repeatPassword) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && isChecked
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

    fun setCheckAcceptTerms(value: Boolean) {
        _isCheckedAcceptTerms.value = value
    }

    fun getCheckAcceptTerms(): Boolean {
        return _isCheckedAcceptTerms.value
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
