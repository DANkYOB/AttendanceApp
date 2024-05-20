package com.example.nelsonfinalyearproject.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nelsonfinalyearproject.databinding.ItemClassUpdateBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

class UpdateClassAdapter(
    private val listener: UpdateClassAdapterListener
): RecyclerView.Adapter<NewsViewHolder>() {


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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemClassUpdateBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.tvSubject.text = notes[position].title
        holder.binding.tvSubjectUpdateReason.text = notes[position].body


    }




}

class NewsViewHolder(
    val binding: ItemClassUpdateBinding
): RecyclerView.ViewHolder(binding.root){


}



