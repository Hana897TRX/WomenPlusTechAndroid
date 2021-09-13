package com.hana897trx.womenplustech.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.MyMessagesBinding
import com.hana897trx.womenplustech.model.Adapter.MessageAdapter
import com.hana897trx.womenplustech.viewmodel.MessagesViewModel


class myCoursesMessages : AppCompatActivity() {
    private var messagesBinding : MyMessagesBinding? = null
    private var messageViewModel : MessagesViewModel? = null
    private var idEvent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messagesBinding = MyMessagesBinding.inflate(layoutInflater)
        idEvent = intent.getStringExtra("_id")!!
        messageViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        messagesBinding?.model = messageViewModel
        messagesBinding?.idEvent = idEvent

        setContentView(messagesBinding!!.root)

        messagesBinding?.btnBack?.setOnClickListener {
            finish()
        }

        loadMessages()
    }

    private fun loadMessages() {
        val messages = messageViewModel?.getMessages(idEvent)
        messages?.observe(this, {
            val adapter = MessageAdapter(this, R.layout.message_layout, it)
            messagesBinding?.rvMessages?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            messagesBinding?.rvMessages?.adapter = adapter
        })
    }

}