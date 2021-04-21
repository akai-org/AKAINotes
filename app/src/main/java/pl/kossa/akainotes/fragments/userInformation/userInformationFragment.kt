package pl.kossa.akainotes.fragments.userInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_user_information.*
import pl.kossa.akainotes.R
import pl.kossa.akainotes.fragments.addNote.AddNoteViewModel
import pl.kossa.akainotes.fragments.notes.NotesFragmentDirections


class userInformationFragment : Fragment() {

    private val viewModel by lazy {
        userInformationViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInformationBackButton.setOnClickListener()
        {
            findNavController().navigate(R.id.action_userInformationFragment_to_notesFragment)
        }

        viewModel.setEmail("email")
        viewModel.setUserName("User name")
        userName.text = viewModel.getUserName()
        userEmail.text = viewModel.getEmail()

    }

}