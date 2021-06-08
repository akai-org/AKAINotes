package pl.kossa.akainotes.fragments.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_note.swipeRefresh
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.fragment_note) {

    private val viewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backArrowViewNote.setOnClickListener {
            findNavController().popBackStack()
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.getNote()
        }

        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.note.collect {
                it?.let { note ->
                    noteTitleNoteView.text = note.title
                    noteContent.text = note.description

                }
            }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                swipeRefresh.isRefreshing = it
            }
        }
    }
}