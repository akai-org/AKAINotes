package pl.kossa.akainotes.fragments.addNote

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import pl.kossa.akainotes.data.Note
import pl.kossa.akainotes.prefs.PrefsHelper

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private val viewModel by lazy {
        AddNoteViewModel(PrefsHelper(requireContext()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitle(text.toString())
        }

        noteContentEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setContent(text.toString())
        }

        addNoteButton.setOnClickListener {
            val note = Note(viewModel.getTitle(), viewModel.getContent())
            viewModel.addNote(note)
        }

        backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        collectObservables()
    }

    private fun collectObservables() {
        lifecycleScope.launch {
            viewModel.isSaveNoteEnabled.collect {
                addNoteButton.isEnabled = it
            }
        }
        viewModel.noteAdded.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            Log.d("AddNoteFragment", "collect")
            progressBar.isVisible = it
            addNoteButton.isVisible = !it
        }
    }
}