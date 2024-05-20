package com.example.nelsonfinalyearproject.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

import com.example.nelsonfinalyearproject.databinding.FragmentUpdateClassBinding
import com.example.nelsonfinalyearproject.util.Note

class UpdateClassFragment:Fragment() {
    private lateinit var binding: FragmentUpdateClassBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateClassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            addNote()
        }

    }

    private fun addNote() {
        val title = binding.inputTitle.editText?.text?.trim()?.toString()
        val body = binding.inputBody.editText?.text?.trim()?.toString()

        if (title.isNullOrEmpty() || body.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter data", Toast.LENGTH_SHORT).show()
            return
        }

        setFragmentResult("note", Bundle().apply {
            putParcelable("note", Note(System.currentTimeMillis().toString(), title, body))
        })
        findNavController().navigateUp()
    }
}