package pl.kossa.akainotes.fragments.userInformation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class userInformationViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _userName = MutableStateFlow("")

    fun setEmail(email:String)
    {
        _email.value = email
    }

    fun setUserName(userName:String)
    {
        _userName.value = userName
    }
    fun getEmail() : String
    {
        return _email.value
    }

    fun getUserName() : String
    {
        return _userName.value
    }


}