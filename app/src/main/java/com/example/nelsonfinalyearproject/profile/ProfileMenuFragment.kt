package com.example.nelsonfinalyearproject.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
        binding.actionLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            GoogleSignIn.getClient(requireContext(), gso).signOut()
            startActivity(intent)
        }



        binding.actionMyProfile.setOnClickListener {
            findNavController().navigate(R.id.myProfileFragment)
        }

        binding.actionSetting1.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })



    }



    private fun initViews() {
        viewLifecycleOwner.lifecycleScope.launch {
            FirebaseUserUtil.getFirebaseUserData()?.let { user ->
                Log.e("user", user.toString())
                withContext(Dispatchers.Main) {
                    binding.tvName.text = user.name
                    binding.tvDepartment.text = user.dept
                    binding.tvRollNo.text = user.rollnumber
                    Glide.with(binding.ivProfile).load(user.photo).into(binding.ivProfile)
                }
            }
        }

    }
}