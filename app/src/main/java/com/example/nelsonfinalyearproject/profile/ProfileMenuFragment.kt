package com.example.nelsonfinalyearproject.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.nelsonfinalyearproject.Auth.AuthActivity
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentProfileMenuBinding
import com.example.nelsonfinalyearproject.util.FirebaseUserUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileMenuFragment: Fragment() {
    private lateinit var binding: FragmentProfileMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileMenuBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()



        binding.actionMyProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        binding.actionSetting1.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }


    }



    private fun initViews() {
        Firebase.auth.currentUser?.let { user ->
            Glide.with(binding.ivProfile).load(user.photoUrl).error(R.drawable.ic_profile_icon)
                .into(binding.ivProfile)


        }

        viewLifecycleOwner.lifecycleScope.launch {
            FirebaseUserUtil.getFirebaseUserData()?.let { user ->
                withContext(Dispatchers.Main) {
                    Glide.with(binding.ivProfile).load(user.photo).into(binding.ivProfile)
                }
            }
        }

    }
}