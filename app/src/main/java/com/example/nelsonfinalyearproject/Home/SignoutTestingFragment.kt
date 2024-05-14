package com.example.nelsonfinalyearproject.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nelsonfinalyearproject.Auth.AuthActivity
import com.example.nelsonfinalyearproject.databinding.FragmentSignoutTestingBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SignoutTestingFragment: Fragment() {

    private lateinit var binding: FragmentSignoutTestingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignoutTestingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(),AuthActivity::class.java)
            startActivity(intent)
        }
    }
}