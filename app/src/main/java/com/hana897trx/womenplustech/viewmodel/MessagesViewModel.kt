package com.hana897trx.womenplustech.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Models.Messages

class MessagesViewModel : ViewModel() {
    private var messages = MutableLiveData<List<Messages>>()
    private var apiMessages : APIMessages = APIMessages()

    fun getMessages(idEvent : String) : MutableLiveData<List<Messages>>{
        messages = apiMessages.getEventMessages(idEvent)
        return messages
    }
}