package com.example.nelsonfinalyearproject.Auth

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.MainActivity
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentSignupBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.auth

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.tvLoginBottom.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.tvForgotPass.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }




        binding.btnSignup.setOnClickListener {
            val email =binding.emailTIL.editText?.text.toString().trim()
            val password = binding.passwordTIL.editText?.text.toString().trim()
            val rePassword = binding.rePasswordTIL.editText?.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Confirmation Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password==rePassword){

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            //Log.d(TAG, "createUserWithEmail:success")
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    Toast.makeText(requireContext(),
                                        "Please Verify your Email",
                                    Toast.LENGTH_SHORT).show()
                                    saveData()

                                }
                                ?.addOnFailureListener{
                                    Toast.makeText(requireContext(), task.toString(),
                                        Toast.LENGTH_SHORT).show()
                                }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                requireContext(),
                                "Verify your email & Login",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            }else{
             Toast.makeText(requireContext(),
                 "Your confirmation password does not match.",
                 Toast.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.loginFragment)


        }


        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }


    private fun saveData(){

        val email =binding.emailTIL.editText?.text.toString().trim()
        val password = binding.passwordTIL.editText?.text.toString().trim()
        val rePassword = binding.rePasswordTIL.editText?.text.toString().trim()

    }
}