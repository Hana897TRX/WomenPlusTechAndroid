package com.hana897trx.womenplustech

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.Adapter.EventAdapter
import com.hana897trx.womenplustech.Models.Event
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fresco.initialize(this);

        spCampus()
        loadEvents()
    }

    private fun spCampus(){
        val campus = arrayOf("Todos los campus", "CVA", "PUE")
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            campus
        )
        spCampus.adapter = adapter
    }

    private fun loadEvents() {
        val db = Firebase.firestore

        val events = arrayListOf<Event>();

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
                        generateUrl(document.get("eventImage").toString())!!
                    )
                    events.add(event)
                    // Log.d(TAG, "${document.id} => ${document.data}")
                }
                val adapter = EventAdapter(this, R.layout.event_layout, events)
                rvEvents.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvEvents.setHasFixedSize(true)
                rvEvents.adapter = adapter
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