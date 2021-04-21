package pl.kossa.akainotes.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import pl.kossa.akainotes.repository.UsersRepository

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by lazy {
        LoginViewModel(UsersRepository())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailTextInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }
        passwordTextInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setPassword(text.toString())
        }
        loginButton.setOnClickListener {
            viewModel.requestLogin()
        }

        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.goToRegisterFragment())
        }

        changePasswordButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.goToChangePassword())
        }

        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.apply {
            launch {
                viewModel.isLoginEnabled.collect {
                    loginButton.isEnabled = it
                }
            }
            launch {
                viewModel.loginSuccess.collect {
                    it?.let {
                        findNavController().navigate(LoginFragmentDirections.goToMainActivity())
                        requireActivity().finish()
                    }
                }
            }
            launch {
                viewModel.loginFailure.collect {
                    it?.let {
                        Toast.makeText(
                            requireContext(),
                            "Wrong email or password!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}