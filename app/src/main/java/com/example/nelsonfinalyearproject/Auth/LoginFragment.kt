package com.example.nelsonfinalyearproject.Auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.MainActivity
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth


        binding.tvSignup.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }

        binding.tvSignupBottom.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }

        binding.tvForgotPass.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }


        binding.btnLogin.setOnClickListener {

            val email = binding.inputEmail.editText?.text.toString().trim()
            val password = binding.inputPassword.editText?.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val verification = auth.currentUser?.isEmailVerified
                        if (verification == true) {
                            val user = auth.currentUser
                            updateUI()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please Verify your Email.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                        Toast.makeText(
                            requireContext(),
                            "Authentication Successful.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            requireContext(),
                            "Please signup and verify your email.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }


        }

    }

    private fun updateUI() {

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

}