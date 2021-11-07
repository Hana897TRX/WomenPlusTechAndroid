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
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date


class EventInfo : AppCompatActivity() {
    private var cardBtnBack : CardView? = null
    private var cardInformation : CardView? = null
    private var imgEvent : SimpleDraweeView? = null
    private var webContent : WebView? = null
    private var apiMessages : APIMessages? = null

    private var event : Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)

        cardBtnBack = findViewById(R.id.cardBtnBack)
        cardInformation = findViewById(R.id.cardInformation)
        imgEvent = findViewById(R.id.imgCourse)
        webContent = findViewById(R.id.webInscrib)

        apiMessages = APIMessages()

        back()
        setInfoToView()
        btnInscribirse()
    }

    private fun back(){
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            if(imgEvent!!.visibility == View.GONE){
                cardInformation!!.visibility = View.VISIBLE
                imgEvent!!.visibility = View.VISIBLE
                webContent!!.visibility = View.GONE
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

            val messages = apiMessages?.getEventMessages(event!!.id)
            messages?.observe(this, { it ->
                lifecycleScope.launch(Dispatchers.IO) {
                    db.messageDao().insertMessages(it)
                }
            })
        }
    }

    private fun setInfoToView(){
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

        imgEvent!!.setImageURI(Uri.parse(event!!.eventImage))
        val txtTitle = findViewById<TextView>(R.id.txtEventTitle)
        txtTitle.text = event!!.title
        val txtEventType = findViewById<TextView>(R.id.txtEventType)
        txtEventType.text = event!!.eventType
        val txtCampus = findViewById<TextView>(R.id.txtEventType)
        txtCampus.text = event!!.campus
        val txtFechaInicio = findViewById<TextView>(R.id.txtFechaInicio)
        txtFechaInicio.text = event!!.fechaInicio.toString()
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        txtDescription.text = event!!.description
        val txtTemary = findViewById<TextView>(R.id.txtTemary)
        txtTemary.text = event!!.temary
    }
}