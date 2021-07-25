package com.hana897trx.womenplustech

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import java.util.*

class myAccount : Fragment() {
    private lateinit var calendar : Calendar
    private var year : Int = 1
    private var month : Int = 1
    private var day : Int = 1
    private var date : DatePickerDialog? = null
    private var txtBirthDay : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_my_account, container, false)

        addFunctions(view)
        calendarConfig(view)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addFunctions(view : View) {
        val cardImg = view.findViewById<CardView>(R.id.cardProfileImg)
        val profileImg = view.findViewById<ImageView>(R.id.imgProfile)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        txtBirthDay = view.findViewById<TextView>(R.id.txtBirthdate)

        txtBirthDay!!.setOnClickListener { date!!.show() }
        btnUpdate.setOnClickListener { updateInformation() }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calendarConfig (view : View) {
        date = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            txtBirthDay!!.text = "" + dayOfMonth + "/" + (month + 1)  + "/" + year
        }, year, month, day)
    }

    private fun updateInformation() {

    }
}