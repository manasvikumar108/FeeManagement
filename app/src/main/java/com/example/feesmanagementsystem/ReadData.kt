package com.example.feesmanagementsystem

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feesmanagementsystem.databinding.ActivityReadDataBinding
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Models.User
class ReadData : AppCompatActivity() {
    private lateinit var binding: ActivityReadDataBinding
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        fetchDataFromDatabase()
    }
    private fun fetchDataFromDatabase() {
        val usersList: List<User> = databaseHelper.getAllUsers()
        if (usersList.isNotEmpty()) {
            val userDetails = usersList.joinToString("\n\n") { user ->
                "Name: ${user.nameTxt}\nEmail: ${user.emailTxt}\nPhone: ${user.phoneTxt}\nTotal Fee: ${user.totalFee}\nPending Fee: ${user.pendingFee}" }
            binding.textViewUsers.text = userDetails
        } else {
            binding.textViewUsers.text = "No users found in the database."
        }
    }
}
