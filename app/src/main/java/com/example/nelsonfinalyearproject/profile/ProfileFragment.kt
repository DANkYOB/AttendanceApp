package com.example.nelsonfinalyearproject.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.nelsonfinalyearproject.Auth.AuthActivity
import com.example.nelsonfinalyearproject.MainActivity
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentProfileBinding
import com.example.nelsonfinalyearproject.util.FirebaseUserUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import com.google.firebase.storage.storageMetadata
import com.yalantis.ucrop.UCrop
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var binding: com.example.nelsonfinalyearproject.databinding.FragmentProfileBinding


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {

            val file = File(requireContext().cacheDir, "temp.png")

            UCrop.of(uri, Uri.fromFile(file))
                .withAspectRatio(1f, 1f)
                .start(requireActivity(), this);
        } else {
            Log.e("PhotoPicker", "No media selected")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()


        binding.btnUploadPhoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }



            binding.btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                val intent = Intent(requireContext(), AuthActivity::class.java)
                val gso = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                GoogleSignIn.getClient(requireContext(),gso).signOut()
                startActivity(intent)
            }


    }

    private fun initViews() {
        Firebase.auth.currentUser?.let {user ->
            Glide.with(binding.ivProfile).load(user.photoUrl).error(R.drawable.ic_profile_icon).into(binding.ivProfile)
            binding.inputName.editText?.setText(user.displayName)

        }
    }


    private fun uploadPhoto(uri: Uri){
        val ref = Firebase.storage.getReference("images").child("${Firebase.auth.currentUser?.uid}.png")
        binding.progressBar.visibility = View.VISIBLE
        val uploadTask = ref.putFile(uri)
        uploadTask.addOnCompleteListener{
            binding.progressBar.visibility = View.GONE
        }.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FirebaseUserUtil.updateUser( photo = task.result){
                    Glide.with(binding.ivProfile).load(task.result).diskCacheStrategy(
                        DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(binding.ivProfile)
                    Toast.makeText(requireContext(), "Photo uploaded successfully", Toast.LENGTH_SHORT).show()
                }
            } else {

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null){
            return
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data) ?: return
            Glide.with(binding.ivProfile).load(resultUri).into(binding.ivProfile)
            uploadPhoto(resultUri)

        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data);
            Toast.makeText(requireContext(), cropError?.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }



}