package com.example.nelsonfinalyearproject.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.nelsonfinalyearproject.Adapters.UpdateClassAdapter
import com.example.nelsonfinalyearproject.R
import com.example.nelsonfinalyearproject.databinding.FragmentHomeBinding
import com.example.nelsonfinalyearproject.util.Note
import com.example.nelsonfinalyearproject.util.NoteSaver
import com.example.nelsonfinalyearproject.util.UpdateClassAdapterListener
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

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

        //classes
        lifecycle.coroutineScope.launch {
            val docs = Firebase.database.reference
                .child("s1")
                .child("sub")
                .get()
                .await()
            val subjects = mutableListOf<SubjectModel>()
            docs.children.forEach {
                val subject = it.getValue(SubjectModel::class.java)!!
                subjects.add(subject)
            }

            //subjects - show in adapter

            val currentTime = System.currentTimeMillis()
            val midnightTime = getMidnightTimestamp()
            val weekDay = getWeekDay()
            Log.e("weekDay", weekDay.toString())

            for (sub in subjects) {
                Log.e("sub", sub.toString())
                val todayTimings = sub.timings?.get(weekDay)
                if (!todayTimings.isNullOrEmpty()) {
                    for (slot in todayTimings) {
                        if (
                            currentTime > (slot[0] * 60 * 1000) + midnightTime &&
                            currentTime < (slot[1] * 60 * 1000) + midnightTime
                        ) {
                            //Class is ongoing
                        } else if (
                            currentTime > (slot[0] * 60 * 1000) + midnightTime &&
                            currentTime > (slot[1] * 60 * 1000) + midnightTime
                        ) {
                            //class ended
                        } else if (currentTime < (slot[0] * 60 * 1000) + midnightTime) {
                            //upcoming class
                        }
                    }
                }
            }

        }

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

    private fun getMidnightTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        return calendar.timeInMillis
    }

    private fun getWeekDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_WEEK) - 2
    }

}