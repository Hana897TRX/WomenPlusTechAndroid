package com.hana897trx.womenplustech.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.API.APIEvents
import com.hana897trx.womenplustech.model.Observable.CampusDataUI
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val api = APIEvents()
    private val sp = application.getSharedPreferences("remember-user", Context.MODE_PRIVATE)
    private val db = AppDB.getInstance(application.applicationContext).userDao()

    private val _eventsUIState : MutableStateFlow<EventsDataUI> = MutableStateFlow(EventsDataUI.Loading(true))
    val eventsUIState = _eventsUIState

    private val _campusUIState : MutableStateFlow<CampusDataUI> = MutableStateFlow(CampusDataUI.Loading(true))
    val campusDataUI = _campusUIState

    private val _userDataUIState : MutableStateFlow<UserDataUI> = MutableStateFlow(UserDataUI.Loading(true))
    val userDataUI = _userDataUIState

    init {
        getCampusData()
        getEventsData()
        getUserData()
    }

    private fun getUserData() = viewModelScope.launch {
        if(sp != null) {
            if(sp.getBoolean("remember", false)) {
                val mail = sp.getString("email", "")
                val password = sp.getString("password", "")

                if(!mail.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    withContext(Dispatchers.IO) {
                        val user = db.logIn(mail, password)
                        if (user != null)
                            _userDataUIState.emit(UserDataUI.Sucess(user))
                        else
                            _userDataUIState.emit(UserDataUI.Error)
                    }
                }
            }
        }
    }

    private fun getCampusData() = viewModelScope.launch {
        when(val response = api.getCampus()) {
            is StateResult.Success -> _campusUIState.emit(CampusDataUI.Success(response.data))
            is StateResult.Loading -> _campusUIState.emit(CampusDataUI.Loading(response.loading))
            is StateResult.Error -> _campusUIState.emit(CampusDataUI.Error)
        }
    }

    private fun getEventsData() = viewModelScope.launch {
        when(val response = api.getEvents()) {
            is StateResult.Success -> _eventsUIState.emit(EventsDataUI.Success(response.data))
            is StateResult.Loading -> _eventsUIState.emit(EventsDataUI.Loading(response.loading))
            is StateResult.Error -> _eventsUIState.emit(EventsDataUI.Error)
        }
    }
}