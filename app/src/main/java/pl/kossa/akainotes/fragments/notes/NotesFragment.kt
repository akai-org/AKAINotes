package pl.kossa.akainotes.fragments.notes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notes.*
import pl.kossa.akainotes.R
import pl.kossa.akainotes.SwipeDeleteCallback
import pl.kossa.akainotes.adapters.NotesRvAdapter
import pl.kossa.akainotes.data.Note
import pl.kossa.akainotes.extensions.makeCancelSnackbar
import pl.kossa.akainotes.fragments.notes.NotesFragmentDirections.Companion.actionNotesFragmentToUserInformationFragment

class NotesFragment : Fragment(R.layout.fragment_notes) {

    val adapter by lazy {
        NotesRvAdapter(arrayListOf())
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
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToUserInformationFragment())
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(swipeDeleteCallback)
        itemTouchHelper.attachToRecyclerView(notesRecyclerView)
        adapter.notes.add(Note("Tyt1", "Notatka1"))
        adapter.notes.add(Note("Tyt2", "Notatka2"))
        adapter.notes.add(Note("Tyt3", "Notatka3"))
        adapter.notes.add(Note("Tyt4", "Notatka4"))
        adapter.notes.add(Note("Tyt5", "Notatka5"))
        adapter.notifyDataSetChanged()
    }
}