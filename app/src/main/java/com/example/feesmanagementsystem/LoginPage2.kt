package com.example.feesmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginPage2 : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var passwd: EditText
    lateinit var loginbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page2)
        supportActionBar?.setTitle("Login")
        email = findViewById(R.id.email)
        passwd = findViewById(R.id.password)
        loginbtn = findViewById(R.id.btn_login)
        loginbtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = passwd.text.toString()
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_LONG).show()
            } else if (!isValidEmail(emailText)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_LONG).show()
            } else if (emailText == "admin@lpu.in" && passwordText == "1234") {
                val intent = Intent(this, FrameMain::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
