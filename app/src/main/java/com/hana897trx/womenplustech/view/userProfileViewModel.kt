package com.hana897trx.womenplustech.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class userProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDB.getInstance(application)
    private var sharedPreference = application.getSharedPreferences("remember-user", Context.MODE_PRIVATE)

    private val userDataUI : MutableStateFlow<UserDataUI> = MutableStateFlow(UserDataUI.Loading(false))
    val _userDataUI = userDataUI

    private val eventsDataUI : MutableStateFlow<EventsDataUI> = MutableStateFlow(EventsDataUI.Loading(false))
    val _eventsDataUI = eventsDataUI

    fun getEventsUser() = viewModelScope.launch(Dispatchers.IO) {
        val events = db.eventDao().getRegisterEvents(sharedPreference.getString("email", "")!!)

        if (events != null)
            eventsDataUI.emit(EventsDataUI.Success(events))
        else
            eventsDataUI.emit(EventsDataUI.Error)
    }

    fun getUserData(idUser : Int) = viewModelScope.launch(Dispatchers.IO) {
        val user = db.userDao().getUser(idUser.toLong())

        if (user != null)
            userDataUI.emit(UserDataUI.Sucess(user))
        else
            userDataUI.emit(UserDataUI.Error)
    }

    fun invalidateUserSesion() {
        sharedPreference!!.edit().apply {
            putString("email", "")
            putString("password", "")
            apply()
        }
    }
}