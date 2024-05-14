package com.example.nelsonfinalyearproject.Auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment: Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

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

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.tvLoginBottom.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.tvForgotPass.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }


        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val email = binding.emailTIL.toString()
            val password = binding.passwordTIL.toString()
            val rePassword = binding.rePasswordTIL.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()){
                if (password == rePassword){

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){

                        }else{
                            Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(requireContext(),"Password is not matching",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Empty Fields are not Allowed ",Toast.LENGTH_SHORT).show()
            }
        }










        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }
}