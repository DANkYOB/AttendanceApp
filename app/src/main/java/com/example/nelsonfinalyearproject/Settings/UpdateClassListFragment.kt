package com.example.nelsonfinalyearproject.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.Adapters.UpdateClassListAdapter
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentClassUpdateListBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.NoteSaver
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

class UpdateClassListFragment : Fragment() {

    private lateinit var binding: FragmentClassUpdateListBinding

    private lateinit var noteSaver: NoteSaver
    private lateinit var adapter: UpdateClassListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassUpdateListBinding.inflate(layoutInflater)
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

        adapter = UpdateClassListAdapter(object : UpdateClassAdapterListener {
            override fun onItemDelete(pos: Int, note: Note) {
                deleteNote(pos, note)


            }
        })

        binding.recyclerViewClassUpdate.adapter = adapter


        val savedNotes = noteSaver.getAllNotes()
        if (savedNotes.isEmpty()) {
            binding.recyclerViewClassUpdate.visibility = View.GONE
        } else {
            binding.recyclerViewClassUpdate.visibility = View.VISIBLE
            adapter.addNotes(savedNotes)
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })

    }

    private fun deleteNote(pos: Int, note: Note) {
        noteSaver.deleteNote(note)
        adapter.deleteNote(pos)

        if (adapter.itemCount == 0) {
            binding.recyclerViewClassUpdate.visibility = View.GONE
        }
    }

    private fun navigateToClassUpdate() {
        findNavController().navigate(R.id.updateClassFragment)
    }

    private fun addNote(note: Note) {
        adapter.addNote(note)
        noteSaver.saveNote(note)
        if (adapter.itemCount > 0) {
            binding.recyclerViewClassUpdate.visibility = View.VISIBLE
        }
    }
}