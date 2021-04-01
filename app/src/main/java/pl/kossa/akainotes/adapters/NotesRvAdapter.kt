package pl.kossa.akainotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.kossa.akainotes.R
import pl.kossa.akainotes.data.Note

class NotesRvAdapter(
    val notes: ArrayList<Note>
) : RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.description.take(50)
    }

    override fun getItemCount() = notes.size

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    }
}


