package com.example.feesmanagementsystem.Models

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import com.example.feesmanagementsystem.R

class Home4 : Fragment() {
    private lateinit var studentNameEditText: EditText
    private lateinit var feesAmountEditText: EditText
    private lateinit var addFeesButton: Button
    private lateinit var feesListView: ListView
    private lateinit var qrCodeImageView: ImageView
    private var feesList: MutableList<String> = mutableListOf()
    private lateinit var feesAdapter: ArrayAdapter<String>
    private val qrCodeImageResourceId: Int = R.drawable.fees_qr_code
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home4, container, false)
        studentNameEditText = view.findViewById(R.id.student_name_edittext)
        feesAmountEditText = view.findViewById(R.id.fees_amount_edittext)
        addFeesButton = view.findViewById(R.id.add_fees_button)
        feesListView = view.findViewById(R.id.fees_listview)
        qrCodeImageView = view.findViewById(R.id.qr_code_imageview)
        feesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, feesList)
        feesListView.adapter = feesAdapter
        qrCodeImageView.setImageResource(qrCodeImageResourceId)
        addFeesButton.setOnClickListener {
            val studentName = studentNameEditText.text.toString()
            val feesAmount = feesAmountEditText.text.toString()
            val feesEntry = "$studentName paid $feesAmount"
            feesList.add(feesEntry)
            feesAdapter.notifyDataSetChanged()
            studentNameEditText.text.clear()
            feesAmountEditText.text.clear()
        }
        return view
    }
}