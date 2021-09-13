package com.hana897trx.womenplustech.model.API

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.Messages
import java.sql.Date

class APIMessages {
    val fb = Firebase.firestore
    private var messages = MutableLiveData<List<Messages>>()

    fun getEventMessages(idEvent : String) : MutableLiveData<List<Messages>> {
        val listOfMessages = arrayListOf<Messages>()

        fb.collection("messages")
            .whereEqualTo("idEvent", idEvent)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){

                    val message = Messages(
                        document.id,
                        document.data["idEvent"].toString(),
                        document.data["message"].toString(),
                        Date(document.getTimestamp("fechaEnvio")!!.seconds)
                    )
                    listOfMessages.add(message)
                }

                listOfMessages.sortByDescending { it.fecha_envio }

                //val adapter = MessageAdapter(this@myCoursesMessages,
                //R.layout.message_layout, listOfMessages)
                //rvMessages.layoutManager = LinearLayoutManager(this@myCoursesMessages, LinearLayoutManager.VERTICAL, false)
                //rvMessages.adapter = adapter
                messages.value = listOfMessages
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        return messages
    }
}