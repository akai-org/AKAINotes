package pl.kossa.akainotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R

class LoginFragment : Fragment() {

    private val viewModel by lazy {
        LoginViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
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
            findNavController().navigate(LoginFragmentDirections.goToMainActivity())
            requireActivity().finish()
        }
        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.goToRegisterFragment())
        }
        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isLoginEnabled.collect {
                loginButton.isEnabled = it
            }
        }
    }
}