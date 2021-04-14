package pl.kossa.akainotes.fragments.addNote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import pl.kossa.akainotes.data.Note

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private val viewModel by lazy {
        AddNoteViewModel()
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
            Toast.makeText(
                requireContext(),
                "Titile: \n${note.title}\nDescription: \n${note.description}\nTODO: Send note to DB",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
            //TODO call api
        }

        backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isSaveNoteEnabled.collect {
                addNoteButton.isEnabled = it
            }
        }
    }
}