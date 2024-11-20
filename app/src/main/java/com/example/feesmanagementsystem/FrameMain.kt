package com.example.feesmanagementsystem

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.feesmanagementsystem.Adapter.MyAdapter
import com.example.feesmanagementsystem.Database.DatabaseHelper
import com.example.feesmanagementsystem.Models.User

class FrameMain : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var emplist: ArrayList<User>
    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        dbHelper = DatabaseHelper(this)
        emplist = arrayListOf()
        getData()
        supportActionBar?.setTitle("Student Details")
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        // Query data from SQLite
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "users",
            arrayOf("id", "name", "reg", "email", "phone", "totalFee", "pendingFee"),
            null, null, null, null, null
        )
        emplist.clear()
        while (cursor.moveToNext()) {
            val user = User(
                nameTxt = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                regTxt = cursor.getString(cursor.getColumnIndexOrThrow("reg")),
                emailTxt = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                phoneTxt = cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                totalFee = cursor.getString(cursor.getColumnIndexOrThrow("totalFee")),
                pendingFee = cursor.getString(cursor.getColumnIndexOrThrow("pendingFee"))
            )
            emplist.add(user)
        }
        cursor.close()
        // Set up the adapter with the updated list
        val mAdapter = MyAdapter(emplist)
        recyclerView.adapter = mAdapter

        mAdapter.setOnClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@FrameMain, ListOfStudents::class.java)
                intent.putExtra("nameTxt", emplist[position].nameTxt)
                intent.putExtra("regTxt", emplist[position].regTxt)
                intent.putExtra("emailTxt", emplist[position].emailTxt)
                intent.putExtra("phoneTxt", emplist[position].phoneTxt)
                intent.putExtra("totalTxt", emplist[position].totalFee)
                intent.putExtra("pendTxt", emplist[position].pendingFee)
                startActivity(intent)
            }
        })

        recyclerView.visibility = View.VISIBLE
    }
}
