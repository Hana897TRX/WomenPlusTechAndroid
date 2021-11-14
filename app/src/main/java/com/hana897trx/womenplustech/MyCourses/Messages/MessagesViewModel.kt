package com.hana897trx.womenplustech.MyCourses.Messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Observable.MyMessagesDataUI
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessagesViewModel : ViewModel() {
    private var apiMessages : APIMessages = APIMessages()

    private val _messagesUIState : MutableStateFlow<MyMessagesDataUI> = MutableStateFlow(MyMessagesDataUI.Loading(true))
    val messagesDataUI = _messagesUIState

    fun getMessages(idEvent : String) = viewModelScope.launch {
        when(val response = apiMessages.getEventMessages(idEvent)) {
            is StateResult.Success -> _messagesUIState.emit(MyMessagesDataUI.Success(response.data))
            is StateResult.Error -> _messagesUIState.emit(MyMessagesDataUI.Error)
        }
    }
}