package pl.kossa.akainotes.fragments.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notes.*
import pl.kossa.akainotes.R
import pl.kossa.akainotes.adapters.NotesRvAdapter
import pl.kossa.akainotes.data.Note

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private val adapter by lazy {
        NotesRvAdapter(arrayListOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = adapter
        adapter.notes.add(Note("Tyt1", "Notatka1"))
        adapter.notes.add(Note("Tyt2", "Notatka2"))
        adapter.notes.add(Note("Tyt3", "Notatka3"))
        adapter.notes.add(Note("Tyt4", "Notatka4"))
        adapter.notes.add(Note("Tyt5", "Notatka5"))
        adapter.notifyDataSetChanged()
    }
}