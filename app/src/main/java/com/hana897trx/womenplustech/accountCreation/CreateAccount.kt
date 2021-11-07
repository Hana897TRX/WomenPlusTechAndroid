package com.hana897trx.womenplustech.accountCreation

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

class CreateAccount : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.create_account, container, false)

        return view
    }
}