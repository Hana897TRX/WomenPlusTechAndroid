package com.hana897trx.womenplustech

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.Adapter.EventAdapter
import com.hana897trx.womenplustech.Models.Event

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fresco.initialize(this);

        spCampus()
        loadEvents()
    }

    private fun spCampus(){
        val spCampusWidget = findViewById<Spinner>(R.id.spCampus)

        val campus = arrayOf("Todos los campus", "CVA", "PUE")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            campus
        )
        spCampusWidget.adapter = adapter
    }

    private fun loadEvents() {
        val db = Firebase.firestore

        val events = arrayListOf<Event>();
        val pueEvents = arrayListOf<Event>();

        db.collection("eventos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var event : Event = Event(
                        document.get("title").toString(),
                        document.get("description").toString(),
                        document.get("schedule").toString(),
                        document.get("campus").toString(),
                        document.get("days").toString(),
                        document.get("requirements").toString(),
                        document.get("registerLink").toString(),
                        generateUrl(document.get("eventImage").toString())!!,
                        document.get("temary").toString(),
                        document.get("eventType").toString()
                    )
                    events.add(event)
                    if(event.campus == "PUE")
                        pueEvents.add(event)
                    // Log.d(TAG, "${document.id} => ${document.data}")
                }
                // All Events
                val rvEvents = findViewById<RecyclerView>(R.id.rvEvents)
                val adapterAll = EventAdapter(this, R.layout.event_layout, events)

                rvEvents.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvEvents.setHasFixedSize(true)
                rvEvents.adapter = adapterAll

                // Pue Events
                val rvPueEvents = findViewById<RecyclerView>(R.id.rvPueEvents)
                val adapterPue = EventAdapter(this, R.layout.event_layout, pueEvents)

                rvPueEvents.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvPueEvents.setHasFixedSize(true)
                rvPueEvents.adapter = adapterPue
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun generateUrl(s: String): String? {
        val p = s.split("/").toTypedArray()
        return "https://drive.google.com/uc?export=download&id=" + p[5]
    }
}