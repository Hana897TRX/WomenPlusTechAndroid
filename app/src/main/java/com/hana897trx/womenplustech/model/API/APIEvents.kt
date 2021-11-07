package com.hana897trx.womenplustech.model.API

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Utility.StateResult
import org.json.JSONArray
import java.sql.Date

class APIEvents {
    val fb = Firebase.firestore
    private var events = MutableLiveData<List<Event>>()

    suspend fun getCampus() : StateResult<List<CampusEntity>>  {
        //val data = arrayListOf<CampusEntity>()
        val data = fb.collection("campus").get()
        /*return if(data.isSuccessful) {

        }
        else if(data.isCanceled) {
            StateResult.Error(500)
        }
            /*.addOnSuccessListener { result ->
                for(document in result)  {
                    val campus = CampusEntity(
                        campusShort = document.get("campusID").toString(),
                        campusId = document.id,
                        campusName = document.get("campusName").toString()
                    )
                    data.add(campus)
                }
            }*/
        /*return if(data.isEmpty())
            StateResult.Loading(true)
        else
            StateResult.Success(data)
         */
         */

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