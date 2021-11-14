package com.hana897trx.womenplustech.MyCourses

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.API.APIMessages
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.MyCoursesDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/** Created by Gabriela Fernanda Soto Ramirez
  * @author : @Hana897TRX
 */

class MyCoursesViewModel(application: Application) : AndroidViewModel(application) {
    private var apiMessages = APIMessages()
    private var db : AppDB = AppDB.getInstance(application.applicationContext)
    private lateinit var events : List<Event>

    private val _myCoursesUIState : MutableStateFlow<EventsDataUI> = MutableStateFlow(EventsDataUI.Loading(true))
    val myCoursesDataUI = _myCoursesUIState

    init {
        getEventsDataFromLocal()
    }

    private fun getEventsDataFromLocal() = viewModelScope.launch(Dispatchers.IO) {
        events = db.eventDao().getRegisterEvents()
        if(events.isNotEmpty()) {
            _myCoursesUIState.emit(EventsDataUI.Success(events))
            getEventsMessagesRemote()
        }
        else
            _myCoursesUIState.emit(EventsDataUI.Error)
    }

    // This function saves the new messages from firebase in local db
    private fun getEventsMessagesRemote() = viewModelScope.launch(Dispatchers.IO)  {
        for(e in events) {
            when(val response = apiMessages.getEventMessages(e.id)) {
                is StateResult.Success -> db.messageDao().insertMessages(response.data)
                is StateResult.Loading -> { /* NOTHING TO DO */ }
                is StateResult.Error -> { Log.e("APIMessages:", "Error with Firebase") }
            }

        }
    }
}