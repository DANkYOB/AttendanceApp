package com.example.nelsonfinalyearproject.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.Adapters.MondayScheduleAdapter
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentModayScheduleBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.NoteSaver
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

class MondayScheduleFragment:Fragment() {
    companion object {

        private const val ADD_NOTE_RESULT = 1267

    }
    private lateinit var binding: FragmentModayScheduleBinding
    private lateinit var noteSaver: NoteSaver
    private lateinit var adapter: MondayScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModayScheduleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        noteSaver = NoteSaver(requireContext())

        setFragmentResultListener("note") { _, bundle ->
            val note = bundle.getParcelable<Note>("note") ?: return@setFragmentResultListener
            addNote(note)
        }

        adapter = MondayScheduleAdapter(object : UpdateClassAdapterListener {
            override fun onItemDelete(pos: Int, note: Note) {
                deleteNote(pos,note)
            }


        })
        binding.recyclerView.adapter = adapter

        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.action_mondayScheduleFragment_to_addMondayScheduleFragment)
        }

        //load all the saved notes
        val savedNotes = noteSaver.getAllNotes()
        if (savedNotes.isEmpty()) {
            binding.animationView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.animationView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            adapter.addNotes(savedNotes)
        }

    }

    private fun deleteNote(pos: Int, note: Note) {
        noteSaver.deleteNote(note)
        adapter.deleteNote(pos)

        if (adapter.itemCount == 0) {
            binding.animationView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }


    private fun addNote(note: Note) {
        adapter.addNote(note)
        noteSaver.saveNote(note)

        if (adapter.itemCount > 0) {
            binding.animationView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }
}