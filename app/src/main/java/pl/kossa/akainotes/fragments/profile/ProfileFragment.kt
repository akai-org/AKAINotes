package pl.kossa.akainotes.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel by lazy {
        ProfileViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInformationBackButton.setOnClickListener {
            findNavController().popBackStack()
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