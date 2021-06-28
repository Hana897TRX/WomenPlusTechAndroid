package com.hana897trx.womenplustech

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import com.facebook.drawee.view.SimpleDraweeView


class eventInfo : AppCompatActivity() {
    private var cardBtnBack : CardView? = null
    private var cardInformation : CardView? = null
    private var imgEvent : SimpleDraweeView? = null
    private var webContent : WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)

        cardBtnBack = findViewById(R.id.cardBtnBack)
        cardInformation = findViewById(R.id.cardInformation)
        imgEvent = findViewById(R.id.imgCourse)
        webContent = findViewById(R.id.webInscrib)

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
            //cardInformation!!.visibility = View.GONE
            //imgEvent!!.visibility = View.GONE

            //webContent!!.loadUrl(intent.getStringExtra("registerLink")!!)
            //webContent!!.visibility = View.VISIBLE

            val url = intent.getStringExtra("registerLink")!!
            val builder = CustomTabsIntent.Builder()
            val customTabIntent = builder.build()
            customTabIntent.launchUrl(this@eventInfo, Uri.parse(url))
        }
    }

    private fun setInfoToView(){
        imgEvent!!.setImageURI(Uri.parse(intent.getStringExtra("eventImage")))
        val txtTitle = findViewById<TextView>(R.id.txtEventTitle)
        txtTitle.text = intent.getStringExtra("title")
        val txtEventType = findViewById<TextView>(R.id.txtCampus)
        txtEventType.text = intent.getStringExtra("eventType")
        val txtCampus = findViewById<TextView>(R.id.txtCampus)
        txtCampus.text = intent.getStringExtra("campus")
        val txtFechaInicio = findViewById<TextView>(R.id.txtFechaInicio)
        txtFechaInicio.text = "Junio 4, 2021"
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        txtDescription.text = intent.getStringExtra("description")
        val txtTemary = findViewById<TextView>(R.id.txtTemary)
        txtTemary.text = intent.getStringExtra("temary")
    }
}