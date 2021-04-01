package pl.kossa.akainotes.fragments.changepassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class ChangePasswordViewModel: ViewModel() {

    private val _email = MutableStateFlow("")


    val isSendMailEnabled = _email.map {  email ->
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun getEmail(): String {
        return _email.value
    }

}