package com.hana897trx.womenplustech.view

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import com.hana897trx.womenplustech.model.Models.User
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Date
import java.util.*

// Documentation of Shared Storage
// https://developer.android.com/training/data-storage/shared/documents-files

class myAccount : Fragment() {
    private lateinit var calendar : Calendar
    private var year : Int = 1
    private var month : Int = 1
    private var day : Int = 1
    private var date : DatePickerDialog? = null
    private var txtBirthDay : TextView? = null
    private var txtUserName : TextView? = null
    private var txtMail : TextView? = null
    private var profileImg : ImageView? = null
    private var coverImg : ImageView? = null
    private var cardCover : CardView? = null
    private var userIsLoaded = false

    private var PROFILE_CODE = 8703
    private var COVER_CODE = 8707

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_my_account, container, false)

        addFunctions(view)
        calendarConfig(view)

        return view
    }

    private fun addFunctions(view : View) {
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        txtMail = view.findViewById<TextView>(R.id.txtMail)
        txtUserName = view.findViewById<TextView>(R.id.txtName)
        profileImg = view.findViewById<ImageView>(R.id.imgProfile)
        coverImg = view.findViewById<ImageView>(R.id.imgCover)
        txtBirthDay = view.findViewById<TextView>(R.id.txtBirthdate)
        cardCover = view.findViewById<CardView>(R.id.cardCover)

        txtBirthDay!!.setOnClickListener { date!!.show() }
        btnUpdate.setOnClickListener { updateInformation(view) }
        profileImg!!.setOnClickListener { changeProfile() }
        coverImg!!.setOnClickListener { changeCover() }
        cardCover!!.setOnClickListener { changeCover() }

        loadContent()
    }

    private fun loadContent() {
        lifecycleScope.launch(Dispatchers.IO){
            val db = AppDB.getInstance(requireContext())
            val user = db.userDao().getUser()

            if(user.size > 0) {
                withContext(Dispatchers.Main) {
                    coverImg!!.setImageBitmap(BitmapFactory.decodeByteArray(user[0].imgCover, 0, user[0].imgCover.size))
                    profileImg!!.setImageBitmap(BitmapFactory.decodeByteArray(user[0].imgProfile, 0, user[0].imgProfile.size))
                    txtBirthDay!!.text = user[0].birthDate.toString()
                    txtMail!!.text = user[0].email
                    txtUserName!!.text = user[0].userName

                    userIsLoaded = true
                }
            }
        }
    }

    private fun calendarConfig (view : View) {
        date = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            txtBirthDay!!.text = "" + year + "-" + (month + 1)  + "-" + dayOfMonth
        }, year, month, day)
    }

    private fun updateInformation(view : View) {
        val txtPassword = view.findViewById<TextView>(R.id.txtPassword)
        val txtConfirmPassword = view.findViewById<TextView>(R.id.txtConfirmPassword)
        val password = txtPassword.text.toString()

        val profileByte = ByteArrayOutputStream()
        profileImg!!.drawable.toBitmap(1280, 720).compress(Bitmap.CompressFormat.PNG, 70, profileByte)

        val coverByte = ByteArrayOutputStream()
        coverImg!!.drawable.toBitmap(1280, 720).compress(Bitmap.CompressFormat.PNG, 70, coverByte)

        if(txtUserName!!.text.length > 5){
            if(txtBirthDay!!.text.length > 0){
                if(txtMail!!.text.length > 0){

                    if(password.length > 0){
                        if(password == txtConfirmPassword.text){
                            Toast.makeText(requireContext(), "Password will be needed to log-in", Toast.LENGTH_SHORT).show()
                            password.sha256()
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "You won't need a password to log-in", Toast.LENGTH_SHORT).show()
                    }

                    val user = User(
                        0,
                        txtUserName!!.text.toString(),
                        Date.valueOf(txtBirthDay!!.text.toString()),
                        txtMail!!.text.toString(),
                        password,
                        profileByte.toByteArray(),
                        coverByte.toByteArray()
                        )

                    lifecycleScope.launch(Dispatchers.IO) {
                        val db = AppDB.getInstance(requireContext())

                        if(userIsLoaded)
                            db.userDao().updateUser(user)
                        else
                            db.userDao().newUser(user)

                        withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(), "Account changes has been saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    private fun changeProfile() {
        val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
        i.setType("image/*")
        startActivityForResult(i, PROFILE_CODE)
    }

    private fun changeCover() {
        val i = Intent(Intent.ACTION_OPEN_DOCUMENT)
        i.setType("image/*")
        startActivityForResult(i, COVER_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PROFILE_CODE && data != null) {
            Log.i("CORE", data!!.data.toString())
            data.data?.also { uri ->

                val parcelFileDescriptor: ParcelFileDescriptor =
                    requireContext().contentResolver.openFileDescriptor(uri, "r")!!
                val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
                val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()

                profileImg!!.setImageBitmap(image)
            }
        }
        else if (requestCode == COVER_CODE && data != null) {
            data.data?.also { uri ->
                val parcelFileDescriptor: ParcelFileDescriptor =
                    requireContext().contentResolver.openFileDescriptor(uri, "r")!!
                val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
                val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                parcelFileDescriptor.close()

                coverImg!!.setImageBitmap(image)
            }
        }
    }
}