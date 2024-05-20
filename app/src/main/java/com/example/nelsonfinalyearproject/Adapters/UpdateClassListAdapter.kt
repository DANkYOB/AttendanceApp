package com.example.nelsonfinalyearproject.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nelsonfinalyearproject.databinding.ItemClassUpdateWithDeleteBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

class UpdateClassListAdapter(
    private val listener: UpdateClassAdapterListener
): RecyclerView.Adapter<ViewHolder2>() {

    private val notes = mutableListOf<Note>()

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size - 1)
    }

    fun addNotes(data: List<Note>) {
        var endIndex = data.size - 1
        if (endIndex < 0) {
            endIndex = 0
        }

        notes.addAll(data)
        notifyItemRangeInserted(endIndex, notes.size - 1)
    }

    fun deleteNote(pos: Int) {
        notes.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemClassUpdateWithDeleteBinding.inflate(layoutInflater, parent, false)
        return ViewHolder2(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.binding.tvSubject.text = notes[position].title
        holder.binding.tvSubjectUpdateReason.text = notes[position].body

        holder.binding.btnDelete.setOnClickListener {
            listener.onItemDelete(position, notes[position])
        }
    }
}


class ViewHolder2(
    val binding: ItemClassUpdateWithDeleteBinding
): RecyclerView.ViewHolder(binding.root) {



}