package com.example.feesmanagementsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Models.User

class ListOfStudents : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvReg: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvPend: TextView
    private lateinit var dbHelper: DatabaseHelper
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_students)
        supportActionBar?.setTitle("Fees Details of Student")
        initView()
        dbHelper = DatabaseHelper(this)
        val regNumber = intent.getStringExtra("regTxt") ?: ""
        val student = dbHelper.getUserByReg(regNumber)
        setValuesToViews(student)
    }
    private fun initView() {
        tvName = findViewById(R.id.nameTV)
        tvReg = findViewById(R.id.regTV)
        tvEmail = findViewById(R.id.emailTV)
        tvPhone = findViewById(R.id.phoneTV)
        tvTotal = findViewById(R.id.feesTotalTV)
        tvPend = findViewById(R.id.feesPendingTV)
    }
    private fun setValuesToViews(student: User?) {
        if (student != null) {
            tvName.text = student.nameTxt
            tvReg.text = student.regTxt
            tvEmail.text = student.emailTxt
            tvPhone.text = student.phoneTxt
            tvTotal.text = student.totalFee
            tvPend.text = student.pendingFee
        } else {
            tvName.text = "No data found"
            tvReg.text = "N/A"
            tvEmail.text = "N/A"
            tvPhone.text = "N/A"
            tvTotal.text = "N/A"
            tvPend.text = "N/A"
        }
    }
}
