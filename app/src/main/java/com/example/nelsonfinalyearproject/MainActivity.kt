package com.example.nelsonfinalyearproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nelsonfinalyearproject.databinding.ActivityMainBinding
import com.example.nelsonfinalyearproject.profile.UserModel
import com.example.nelsonfinalyearproject.util.SharedPref
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedPref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setOnItemSelectedListener {
            if (it.itemId == R.id.action_home) {
                navController.navigate(R.id.homeFragment)
            }
            if (it.itemId == R.id.action_profile) {
                navController.navigate(R.id.profileMenuFragment)
            }



            return@setOnItemSelectedListener true
        }
    }

    fun updateUser() {
        fun updateUser() {
            val user = Firebase.auth.currentUser ?: return
            Firebase.firestore.collection("users").document(user.uid).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.toObject(UserModel::class.java)?.let {
                        sharedPref.setUser(it)
                    }
                }
            }
        }
    }
}