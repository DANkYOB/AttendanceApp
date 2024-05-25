package com.example.nelsonfinalyearproject.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nelsonfinalyearproject.databinding.ItemTodayClassBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

class MondayScheduleAdapter (
    private val listener: UpdateClassAdapterListener
): RecyclerView.Adapter<StudentViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        //create the view
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodayClassBinding.inflate(layoutInflater, parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        //list count
        return notes.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //data bind
        holder.binding.tvSubject.text = notes[position].title
        holder.binding.tvClassTime.text = notes[position].body

        holder.binding.btnDelete.setOnClickListener {
            listener.onItemDelete(position, notes[position])
        }
    }

}

class StudentViewHolder(
    val binding: ItemTodayClassBinding
): RecyclerView.ViewHolder(binding.root) {



}