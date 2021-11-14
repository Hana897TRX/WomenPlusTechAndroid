package com.hana897trx.womenplustech.view

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.ActivityEventInfoBinding
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date


class EventInfo : AppCompatActivity() {
    private lateinit var binding : ActivityEventInfoBinding

    private var event : Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventInfoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        back()
        setInfoToView()
        btnInscribirse()
    }

    private fun back() = binding.apply {
        btnBack.setOnClickListener {
            if(imgCourse.visibility == View.GONE){
                cardInformation.visibility = View.VISIBLE
                imgCourse.visibility = View.VISIBLE
                webInscrib.visibility = View.GONE
            }
            else
                finish()
        }
    }

    private fun btnInscribirse(){
        val btnInscri = findViewById<Button>(R.id.btnInscribirse)

        btnInscri.setOnClickListener {
            val url = intent.getStringExtra("registerLink")!!
            val builder = CustomTabsIntent.Builder()
            val customTabIntent = builder.build()
            customTabIntent.launchUrl(this@EventInfo, Uri.parse(url))

            val db = AppDB.getInstance(this@EventInfo)

            lifecycleScope.launch(Dispatchers.IO){
                db.eventDao().registerEvent(event!!)

                withContext(Dispatchers.Main){
                    Toast.makeText(this@EventInfo, "Te has registrado a este evento correctamente", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setInfoToView() = binding.apply {
        event = Event(
            intent.getStringExtra("id")!!,
            intent.getStringExtra("title")!!,
            intent.getStringExtra("description")!!,
            intent.getStringExtra("schedule")!!,
            intent.getStringExtra("campus")!!,
            intent.getStringExtra("days")!!,
            intent.getStringExtra("requirements")!!,
            intent.getStringExtra("registerLink")!!,
            intent.getStringExtra("eventImage")!!,
            intent.getStringExtra("temary")!!,
            intent.getStringExtra("eventType")!!,
            Date.valueOf(intent.getStringExtra("fechaInicio"))
        )

        imgCourse.setImageURI(Uri.parse(event!!.eventImage))
        txtEventTitle.text = event!!.title
        txtEventType.text = event!!.eventType
        txtCampus.text = event!!.campus
        txtFechaInicio.text = event!!.fechaInicio.toString()
        txtDescription.text = event!!.description
        txtTemary.text = event!!.temary
    }
}