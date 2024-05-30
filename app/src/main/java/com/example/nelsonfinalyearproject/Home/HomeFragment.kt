package com.example.nelsonfinalyearproject.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.nelsonfinalyearproject.Adapters.UpdateClassAdapter
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentHomeBinding
import com.example.nelsonfinalyearproject.util.FirebaseUserUtil
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.NoteSaver
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    companion object {

        private const val ADD_NOTE_RESULT = 1459

    }

    private lateinit var binding: FragmentHomeBinding

    private lateinit var noteSaver: NoteSaver
    private lateinit var adapter: UpdateClassAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        binding.tvMonday.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mondayScheduleFragment2)
        }

        binding.tvTuesday.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tuesdayFragment)
        }

        binding.tvWednesday.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wednesdayFragment)
        }

        binding.tvThursday.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_thursdayFragment)
        }

        binding.tvFriday.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fridayFragment)
        }
        binding.clickPhoto.setOnClickListener{
        findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }






        binding.tvViewAllClassUpdate.setOnClickListener {
            findNavController().navigate(R.id.updateClassListFragment)
        }


        noteSaver = NoteSaver(requireContext())

        setFragmentResultListener("note") { _, bundle ->
            val note = bundle.getParcelable<Note>("note") ?: return@setFragmentResultListener
            addNote(note)
        }

        adapter = UpdateClassAdapter(object : UpdateClassAdapterListener {
            override fun onItemDelete(pos: Int, note: Note) {

            }
        })






        binding.recyclerViewClassUpdate.adapter = adapter

        binding.tvAddUpdate.setOnClickListener {
            navigateToClassUpdate()
        }

        //load all the saved notes
        val savedNotes = noteSaver.getAllNotes()
        if (savedNotes.isEmpty()) {
            binding.recyclerViewClassUpdate.visibility = View.GONE
        } else {
            binding.recyclerViewClassUpdate.visibility = View.VISIBLE
            adapter.addNotes(savedNotes)
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


    private fun initViews() {
        viewLifecycleOwner.lifecycleScope.launch {
            FirebaseUserUtil.getFirebaseUserData()?.let { user ->
                Log.e("user", user.toString())
                withContext(Dispatchers.Main) {
                    binding.tvStudentName.text = user.name
                    binding.tvStudentDept.text = user.dept
                    Glide.with(binding.profilePic).load(user.photo).into(binding.profilePic)
                }
            }
        }

    }
}

//classes
//        lifecycle.coroutineScope.launch {
//            val docs = Firebase.database.reference
//                .child("s1")
//                .child("sub")
//                .get()
//                .await()
//            val subjects = mutableListOf<SubjectModel>()
//            docs.children.forEach {
//                val subject = it.getValue(SubjectModel::class.java)!!
//                subjects.add(subject)
//            }
//        }