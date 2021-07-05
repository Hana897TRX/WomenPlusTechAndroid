package com.hana897trx.womenplustech

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.Adapter.EventAdapter
import com.hana897trx.womenplustech.Models.Event
import java.sql.Date

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fresco.initialize(requireContext());
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        spCampus(view)
        loadEvents(view)

        return view;
    }

    private fun spCampus(view : View){
        val spCampusWidget = view.findViewById<Spinner>(R.id.spCampus)

        val campus = arrayOf("Todos los campus", "CVA", "PUE")
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            campus
        )
        spCampusWidget.adapter = adapter
    }

    private fun loadEvents(view : View) {
        val db = Firebase.firestore

        val events = arrayListOf<Event>();
        val pueEvents = arrayListOf<Event>();

        db.collection("eventos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val event : Event = Event(
                        document.id,
                        document.get("title").toString(),
                        document.get("description").toString(),
                        document.get("schedule").toString(),
                        document.get("campus").toString(),
                        document.get("days").toString(),
                        document.get("requirements").toString(),
                        document.get("registerLink").toString(),
                        generateUrl(document.get("eventImage").toString())!!,
                        document.get("temary").toString(),
                        document.get("eventType").toString(),
                        Date.valueOf(document.get("fechaInicio").toString())
                    )
                    events.add(event)

                    if(event.campus == "PUE")
                        pueEvents.add(event)
                }

                // All Events
                val rvEvents = view.findViewById<RecyclerView>(R.id.rvEvents)
                val adapterAll = EventAdapter(requireContext(), R.layout.event_layout, events)

                rvEvents!!.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvEvents.setHasFixedSize(true)
                rvEvents.adapter = adapterAll

                // Pue Events
                val rvPueEvents = view.findViewById<RecyclerView>(R.id.rvPueEvents)
                val adapterPue = EventAdapter(requireContext(), R.layout.event_layout, pueEvents)

                rvPueEvents!!.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvPueEvents.setHasFixedSize(true)
                rvPueEvents.adapter = adapterPue
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun generateUrl(s: String): String? {
        val p = s.split("/").toTypedArray()
        return "https://drive.google.com/uc?export=download&id=" + p[5]
    }
}