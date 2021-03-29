package pl.kossa.akainotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import kotlinx.android.synthetic.main.fragment_login.registerButton

class RegisterFragment: Fragment() {
    private val viewModel = RegisterViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailInputEditText.doOnTextChanged{text, _, _, _ ->
            viewModel.setOldPassword(text.toString())
        }

        passwordInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setPassword(text.toString())
        }

        repeatPasswordInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setRepeatPassword(text.toString())
        }

        registerCheckBox.setOnClickListener {
            if (registerCheckBox.isChecked) viewModel.setCheckAcceptTerms("true") else viewModel.setCheckAcceptTerms("false")
        }

        collectFlow()

        registerButton.setOnClickListener {
            val email: String = viewModel.getEmail()
            val passwd: String = viewModel.getPassowrd()
            val snackbar = Snackbar.make(view, "Email: $email\nPassword: $passwd", 1000)
            snackbar.show()
        }
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isChangePasswordEnabled.collect {
                registerButton.isEnabled = it
            }
        }
    }
}