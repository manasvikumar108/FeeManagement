package com.example.feesmanagementsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Models.student_viewPager
import com.google.android.material.tabs.TabLayout

class StudentProfile : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        dbHelper = DatabaseHelper(this)
        viewPager = findViewById(R.id.student_swipe)
        tabLayout = findViewById(R.id.student_tabs)
        val email = intent.getStringExtra("emailTxt") ?: "Unknown Email"
        val viewPagerAdapter = student_viewPager(supportFragmentManager, dbHelper, email)
        viewPager.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }
}
