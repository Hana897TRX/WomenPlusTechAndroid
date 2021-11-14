package com.hana897trx.womenplustech.model.API

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.tasks.await
import java.sql.Date

class APIMessages {
    val fb = Firebase.firestore
    private var messages = MutableLiveData<List<Messages>>()
    private var messageCount = MutableLiveData<Int>()

    fun getEventMerssagesCount(idEvent: String) : MutableLiveData<Int> {
        messageCount.value = 0

        fb.collection("messages")
            .whereEqualTo("idEvent", idEvent)
            .get()
            .addOnSuccessListener { documents ->
                messageCount.value = documents.size()
            }
            .addOnFailureListener {
                messageCount.value = 0
            }

        return messageCount
    }

    suspend fun getEventMessages(idEvent : String) : StateResult<List<Messages>> {
        val documents = fb.collection("messages")
            .whereEqualTo("idEvent", idEvent)
            .get()
            .await()
        if(!documents.isEmpty) {
            val data = arrayListOf<Messages>()
            for (document in documents){
                val message = Messages(
                    document.id,
                    document.data["idEvent"].toString(),
                    document.data["message"].toString(),
                    Date(document.getTimestamp("fechaEnvio")!!.seconds),
                    false
                )
                data.add(message)
            }
            return StateResult.Success(data)
        }
        else
            return StateResult.Error(500)
    }
}