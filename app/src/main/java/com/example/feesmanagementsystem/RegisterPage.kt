package com.example.feesmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Models.User
class RegisterPage : AppCompatActivity() {
    lateinit var etname: EditText
    lateinit var etreg: EditText
    lateinit var etphone: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnReg: Button
    lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        etname = findViewById(R.id.name)
        etreg = findViewById(R.id.regNo)
        etphone = findViewById(R.id.PhoneNo)
        etEmail = findViewById(R.id.email)
        etPassword = findViewById(R.id.password)
        btnReg = findViewById(R.id.btn_register)
        dbHelper = DatabaseHelper(this)
        btnReg.setOnClickListener {
            val nameTxt = etname.text.toString()
            val regTxt = etreg.text.toString()
            val phoneTxt = etphone.text.toString()
            val emailTxt = etEmail.text.toString()
            val passTxt = etPassword.text.toString()
            val totalFee = 10000
            val pendingFee = 2000
            if (nameTxt.isEmpty() || regTxt.isEmpty() || phoneTxt.isEmpty() || emailTxt.isEmpty() || passTxt.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show()
            } else if (!emailTxt.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex())) {
                Toast.makeText(this, "Enter Valid Email Address", Toast.LENGTH_LONG).show()
            } else if (phoneTxt.length != 10) {
                Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_LONG).show()
            } else if (passTxt.length < 6) {
                Toast.makeText(this, "Password cannot be less than 6 characters", Toast.LENGTH_LONG).show()
            } else {
                val user = User(nameTxt, regTxt, emailTxt, phoneTxt, totalFee.toString(), pendingFee.toString(), passTxt)
                val result = dbHelper.insertUser(user)
                if (result != -1L) {
                    Toast.makeText(this, "Registration Successfull", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, StudentProfile::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
