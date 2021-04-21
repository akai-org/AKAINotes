package pl.kossa.akainotes.fragments.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.kossa.akainotes.repository.UsersRepository

class LoginViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _login = MutableStateFlow(false)
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val loginSuccess =
        combine(_login, usersRepository.userAlreadyLoggedIn) { login, isAlreadyLogged ->
            return@combine if(login || isAlreadyLogged) true else null
        }

    val isLoginEnabled = combine(_email, _password) { email, password ->
        return@combine password.isNotBlank() && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    val loginFailure = loginSuccess.map { if (it == false) true else null }


    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun requestLogin() = viewModelScope.launch {
        val success =
            _email.value == usersRepository.email && _password.value == usersRepository.password
        _login.value = success
        // Tutaj jakby była jakaś bardziej zaawansowana logika to loginFailure można
        // było by ustawiać na jakąś klasę błędu zamiast po prostu !succes
    }

    fun getEmail(): String {
        return _email.value
    }

    fun getPassword(): String {
        return _password.value
    }
}