package pl.kossa.akainotes.fragments.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.kossa.akainotes.repository.UsersRepository

class LoginViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _login = MutableSharedFlow<Unit>()
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val isLoginEnabled = combine(_email, _password) { email, password ->
        return@combine password.isNotBlank() && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    private val loginSuccessOrFailure = _login
        .map {
            _email.value == usersRepository.email &&
                    _password.value == usersRepository.password
        }

    val loginSuccess = merge(loginSuccessOrFailure, usersRepository.userAlreadyLoggedIn)
        .filter { it }
        .map { Unit }

    val loginFailure = loginSuccessOrFailure
        .filter { !it }
        .map { Unit }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun requestLogin() = viewModelScope.launch{
        _login.emit(Unit)
    }

    fun getEmail(): String {
        return _email.value
    }

    fun getPassword(): String {
        return _password.value
    }
}