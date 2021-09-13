package com.hana897trx.womenplustech.model.API

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.Event
import org.json.JSONArray
import java.sql.Date

class APIEvents {
    val fb = Firebase.firestore
    private var campus = MutableLiveData<JSONArray>()
    private var events = MutableLiveData<List<Event>>()

    fun getCampus() : MutableLiveData<JSONArray>  {
        val data = JSONArray()
        fb.collection("campus")
            .get()
            .addOnSuccessListener { result ->
                for(document in result)  {
                    data.put(document.get("campusID").toString())
                }
                campus.value = data
            }
        return campus
    }

    fun getEvents() : MutableLiveData<List<Event>> {
        val data = arrayListOf<Event>()
        fb.collection("eventos")
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
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
                    data.add(event)
                }
                events.value = data
            }
        return events
    }

    private fun generateUrl(s: String): String? {
        val p = s.split("/").toTypedArray()
        return "https://drive.google.com/uc?export=download&id=" + p[5]
    }


}