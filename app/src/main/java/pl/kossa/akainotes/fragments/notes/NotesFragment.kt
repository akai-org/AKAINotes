package pl.kossa.akainotes.fragments.notes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.kossa.akainotes.R
import pl.kossa.akainotes.SwipeDeleteCallback
import pl.kossa.akainotes.adapters.NotesRvAdapter
import pl.kossa.akainotes.extensions.makeCancelSnackbar

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val viewModel: NotesViewModel by viewModels()

    val adapter by lazy {
        NotesRvAdapter(arrayListOf()) { note ->
            val direction = NotesFragmentDirections.goToNote(note.id)
            findNavController().navigate(direction)
        }
    }

    private val swipeDeleteCallback by lazy {
        object : SwipeDeleteCallback(requireContext()) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    Toast.makeText(requireContext(), R.string.edit, Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                } else if (direction == ItemTouchHelper.START) {
                    val position = viewHolder.adapterPosition
                    val item = adapter.notes[position]
                    adapter.notes.remove(item)
                    adapter.notifyItemRemoved(position)
                    makeCancelSnackbar(
                        root,
                        R.string.note_delete_question,
                        onCancel = {
                            adapter.notes.add(position, item)
                            adapter.notifyItemInserted(position)
                        },
                        onTimeout = {
                            // TODO call api
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNotesButton.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.goToAddNote())
        }

        userInformationButton.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.goToProfile())
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.getNotes()
        }
        setupRecyclerView()
        collectFlow()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.notesList.collect {
                adapter.notes.clear()
                adapter.notes.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                swipeRefresh.isRefreshing = it
            }
        }
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(swipeDeleteCallback)
        itemTouchHelper.attachToRecyclerView(notesRecyclerView)
    }
}