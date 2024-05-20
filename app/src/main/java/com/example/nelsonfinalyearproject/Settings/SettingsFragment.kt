package com.example.nelsonfinalyearproject.Settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentSettingsBinding

class SettingsFragment:Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUpdateClass.setOnClickListener {
            findNavController().navigate(R.id.updateClassFragment)
        }

        binding.btnUpdateTodayClass.setOnClickListener {
            findNavController().navigate(R.id.updateClassFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}