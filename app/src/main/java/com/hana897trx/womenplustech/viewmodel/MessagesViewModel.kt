package com.hana897trx.womenplustech.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Adapter.MessageAdapter
import com.hana897trx.womenplustech.model.Models.Messages

class MessagesViewModel : ViewModel() {
    private var messages = MutableLiveData<List<Messages>>()
    private var apiMessages : APIMessages = APIMessages()

    companion object {
        @JvmStatic
        @BindingAdapter("adapterMessages")
        fun setMessages(rv : RecyclerView, messages : List<Messages>) {
            val adapter = MessageAdapter(rv.context, R.layout.message_layout, messages)
            rv.adapter = adapter
        }
    }

    fun getMessages(idEvent : String) : MutableLiveData<List<Messages>>{
        messages = apiMessages.getEventMessages(idEvent)
        return messages
    }
}