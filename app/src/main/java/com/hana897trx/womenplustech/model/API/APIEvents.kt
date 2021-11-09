package com.hana897trx.womenplustech.model.API

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.tasks.await
import java.sql.Date

class APIEvents {
    private val fb = Firebase.firestore

    suspend fun getCampus() : StateResult<List<CampusEntity>>  {
        val response = fb.collection("campus").get().await()
        if(!response.isEmpty) {
            val data = arrayListOf<CampusEntity>()
            for(document in response.documents)  {
                val campus = CampusEntity(
                    campusShort = document.get("campusID").toString(),
                    campusID = document.id,
                    campusName = document.get("campusName").toString()
                )
                data.add(campus)
            }
            return StateResult.Success(data)
        }
        return StateResult.Error(500)
    }

    suspend fun getEvents() : StateResult<List<Event>> {
        val documents = fb.collection("eventos").get().await()
        if (!documents.isEmpty) {
            val data = arrayListOf<Event>()
            for (document in documents) {
                val event = Event(
                    id = document.id,
                    title = document.get("title").toString(),
                    description = document.get("description").toString(),
                    schedule = document.get("schedule").toString(),
                    campus = document.get("campus").toString(),
                    days = document.get("days").toString(),
                    requirements = document.get("requirements").toString(),
                    registerLink = document.get("registerLink").toString(),
                    eventImage = generateUrl(document.get("eventImage").toString())!!,
                    temary = document.get("temary").toString(),
                    eventType = document.get("eventType").toString(),
                    fechaInicio = Date.valueOf(document.get("fechaInicio").toString())
                )
                data.add(event)
            }
            return StateResult.Success(data)
        }
        return StateResult.Error(500)
    }

    private fun generateUrl(s: String): String {
        val p = s.split("/").toTypedArray()
        return "https://drive.google.com/uc?export=download&id=" + p[5]
    }
}