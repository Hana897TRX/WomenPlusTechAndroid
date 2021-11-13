package com.hana897trx.womenplustech.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.ActivityMyCoursesMessagesBinding
import com.hana897trx.womenplustech.model.Adapter.MessageAdapter
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.viewmodel.MessagesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class myCoursesMessages : AppCompatActivity() {

    private lateinit var messagesBinding : ActivityMyCoursesMessagesBinding
    //private var messageViewModel : MessagesViewModel? = null
    private var idEvent = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messagesBinding = ActivityMyCoursesMessagesBinding.inflate(layoutInflater)
        /*
        idEvent = intent.getStringExtra("_id")!!
        messageViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)

        setContentView(messagesBinding.root)

        messagesBinding.btnBack.setOnClickListener {
            finish()
        }

        loadMessages()
        setAsSeen()
         */
    }

    private fun loadMessages() {
        /*
        val messages = messageViewModel?.getMessages(idEvent)
        messages?.observe(this, {
            val adapter = MessageAdapter(this, R.layout.message_layout, it)
            messagesBinding.rvMessages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
            messagesBinding.rvMessages.adapter = adapter
        })
         */
    }

    private fun setAsSeen() {
        /*
        val db = AppDB.getInstance(this@myCoursesMessages)
        lifecycleScope.launch(Dispatchers.IO) {
            db.messageDao().updateSeenAt(idEvent)
        }
         */
    }

}