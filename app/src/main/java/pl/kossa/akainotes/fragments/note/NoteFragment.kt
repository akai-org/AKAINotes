package pl.kossa.akainotes.fragments.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_note.*
import pl.kossa.akainotes.R

class NoteFragment() : Fragment(R.layout.fragment_note) {

    val args: NoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteTitleNoteView.text = args.title
        noteContent.text = args.description

        backArrowViewNote.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}