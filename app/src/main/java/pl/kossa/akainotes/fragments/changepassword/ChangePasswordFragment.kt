package pl.kossa.akainotes.fragments.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R

class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {

    private val viewModel by lazy {
        ChangePasswordViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailTextInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

        changePasswordButton.setOnClickListener {
          //TODO SEND EMAIL WITH CHANGE PASSWORD
        }
        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isSendMailEnabled.collect {
                changePasswordButton.isEnabled = it
            }
        }
    }

}