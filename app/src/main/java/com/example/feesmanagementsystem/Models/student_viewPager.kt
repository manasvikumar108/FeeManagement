package com.example.feesmanagementsystem.Models

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Home3
import com.example.feesmanagementsystem.Models.Home4

class student_viewPager(
    fm: FragmentManager,
    private val dbHelper: DatabaseHelper,
    private val studentEmail: String
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()

        // Fetch user data from database using the provided email
        val user = dbHelper.getUserByEmail(studentEmail)

        return when (position) {
            0 -> {
                // Pass user data to Home3 fragment
                val fragment = Home3()
                bundle.putString("name", user?.nameTxt)
                bundle.putString("regNo", user?.regTxt)
                bundle.putString("email", user?.emailTxt)
                bundle.putString("phone", user?.phoneTxt)
                bundle.putString("totalFee", user?.totalFee)
                bundle.putString("pendingFee", user?.pendingFee)
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                // Pass fee-related data to Home4 fragment
                val fragment = Home4()
                bundle.putString("totalFee", user?.totalFee)
                bundle.putString("pendingFee", user?.pendingFee)
                fragment.arguments = bundle
                fragment
            }
            else -> throw IllegalStateException("Invalid position $position")
        }
    }

    override fun getCount(): Int {
        return 2 // Number of tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Student Details"
            1 -> "Pay"
            else -> "Tab"
        }
    }
}
