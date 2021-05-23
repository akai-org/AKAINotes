package pl.kossa.akainotes.fragments.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import pl.kossa.akainotes.prefs.PrefsHelper

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by lazy {
        LoginViewModel(PrefsHelper(requireContext()))
    }

    private val prefsHelper by lazy {
        PrefsHelper(requireContext())
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
            prefsHelper.email = viewModel.getEmail()
            viewModel.login()
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
        lifecycleScope.launch {
            viewModel.isLoginEnabled.collect {
                loginButton.isEnabled = it
            }
        }
        viewModel.directionLiveData.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(it)
                if(it == LoginFragmentDirections.goToMainActivity()) {
                    requireActivity().finish()
                }
            }
        }
    }
}