package pl.kossa.akainotes.fragments.userInformation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_user_information.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R


class UserInformationFragment : Fragment(R.layout.fragment_user_information) {

    private val viewModel by lazy {
        UserInformationViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInformationBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_userInformationFragment_to_notesFragment)
        }

        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.email.collect {
                userEmail.text = it
            }
            viewModel.userName.collect {
                userName.text = it
            }
        }
    }
}