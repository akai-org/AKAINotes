package pl.kossa.akainotes.fragments.viewNote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_view_note.*
import pl.kossa.akainotes.R

class NoteViewFragment : Fragment(R.layout.fragment_view_note) {

    private val viewModel = NoteViewViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteTitleNoteView.text = viewModel.getTitle()
        noteContent.text = viewModel.getContent()

        backArrowViewNote.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}