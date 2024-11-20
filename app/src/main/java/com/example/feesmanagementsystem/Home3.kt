package com.example.feesmanagementsystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.feesmanagementsystem.Database.DatabaseHelper
class Home3 : Fragment() {

    var email: String? = ""
    lateinit var emailtxt: TextView
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home3, container, false)
        dbHelper = DatabaseHelper(requireContext())
        email = getEmailFromDatabase()
        emailtxt = view.findViewById(R.id.emailTV)
        emailtxt.text = email
        return view
    }
    private fun getEmailFromDatabase(): String? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "users",
            arrayOf("email"),
            null, null, null, null, null
        )
        var email: String? = null
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
        }
        cursor.close()
        return email
    }
}
