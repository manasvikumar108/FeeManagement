package com.example.feesmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.feesmanagementsystem.Database.DatabaseHelper

class LoginPage : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var passwd: EditText
    lateinit var loginbtn: Button
    lateinit var registerNow: Button
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        supportActionBar?.setTitle("Login")

        email = findViewById(R.id.email)
        passwd = findViewById(R.id.password)
        loginbtn = findViewById(R.id.btn_login)
        registerNow = findViewById(R.id.register_now)
        dbHelper = DatabaseHelper(this)
        loginbtn.setOnClickListener {
            val emailTxt = email.text.toString()
            val passwdTxt = passwd.text.toString()
            if (emailTxt.isEmpty() || passwdTxt.isEmpty()) {
                Toast.makeText(this, "Enter both the fields", Toast.LENGTH_LONG).show()
            } else {
                loginUser(emailTxt, passwdTxt)
            }
        }

        registerNow.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(emailTxt: String, passwdTxt: String) {
        val user = dbHelper.getUserByEmailAndPassword(emailTxt, passwdTxt)
        if (user != null) {
            Toast.makeText(this, "User has logged in Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentProfile::class.java)
            intent.putExtra("emailTxt", emailTxt)  // Pass email to profile
            startActivity(intent)
        } else {
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_LONG).show()
        }
    }
}
