package pl.kossa.akainotes.fragments.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import pl.kossa.akainotes.api.RetrofitClient
import pl.kossa.akainotes.api.models.LoginRequest
import pl.kossa.akainotes.api.models.TokenResponse
import pl.kossa.akainotes.prefs.PrefsHelper
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val prefsHelper: PrefsHelper) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel_"
    }

    init {
        Log.d(TAG, "PrefsHelper hash: ${prefsHelper.hashCode()}")
    }

    private val retrofitClient = RetrofitClient(prefsHelper)

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    val directionLiveData = MutableLiveData<NavDirections?>(null)

    val isLoginEnabled = combine(_email, _password) { email, password ->
        return@combine password.isNotBlank() && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    val tokenResponse = MutableLiveData<TokenResponse>()

    fun login() {
        viewModelScope.launch {
            try {
                val response = retrofitClient.login(LoginRequest(_email.value, _password.value))
                prefsHelper.token = response.token
                directionLiveData.value = LoginFragmentDirections.goToMainActivity()
                Log.d("MyLog", "Login: $response")
            } catch (e: Exception) {
                Log.e("MyLog", "Exception: $e")
            }
        }
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