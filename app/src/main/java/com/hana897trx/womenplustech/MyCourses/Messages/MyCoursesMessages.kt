package com.hana897trx.womenplustech.MyCourses.Messages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.databinding.ActivityMyCoursesMessagesBinding
import com.hana897trx.womenplustech.model.Adapter.MessageAdapter
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.model.Observable.MyMessagesDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



class MyCoursesMessages : AppCompatActivity() {

    private lateinit var binding : ActivityMyCoursesMessagesBinding
    private lateinit var viewModel : MessagesViewModel

    private var idEvent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCoursesMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idEvent = intent.getStringExtra("_id")!!
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        viewModel.getMessages(idEvent)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                messagesObservable()
                setAsSeen()
            }
        }
        setBackBtn()
    }

    private suspend fun messagesObservable() {
        lifecycleScope.launch {
            viewModel.messagesDataUI.collect {
                when(it) {
                    is MyMessagesDataUI.Success -> setMessages(it.data)
                    is MyMessagesDataUI.Loading -> { /* NOTHING TO DO*/}
                    is MyMessagesDataUI.Error -> { }
                }
            }
        }
    }

    private fun setBackBtn() = binding.apply {
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setMessages(data : List<Messages>) = binding.apply{
        rvMessages.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, true)
        rvMessages.adapter = MessageAdapter(root.context, R.layout.message_layout, data)
    }

    private fun setAsSeen() = lifecycleScope.launch(Dispatchers.IO) {
        val db = AppDB.getInstance(this@MyCoursesMessages)
        db.messageDao().updateSeenAt(idEvent)
    }

}