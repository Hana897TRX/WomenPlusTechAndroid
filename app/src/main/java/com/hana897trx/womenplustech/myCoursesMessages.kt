package com.hana897trx.womenplustech

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.Adapter.MessageAdapter
import com.hana897trx.womenplustech.Models.Messages
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class myCoursesMessages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_courses_messages)

        loadMessages()
    }

    fun loadMessages(){
        val db = Firebase.firestore
        val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
        val listOfMessages = arrayListOf<Messages>()

        db.collection("messages")
            .whereEqualTo("idEvent", intent.getStringExtra("_id"))
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

                val adapter = MessageAdapter(this@myCoursesMessages, R.layout.message_layout, listOfMessages)
                rvMessages.layoutManager = LinearLayoutManager(this@myCoursesMessages, LinearLayoutManager.VERTICAL, false)
                rvMessages.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}