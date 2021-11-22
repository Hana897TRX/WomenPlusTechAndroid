package com.hana897trx.womenplustech.model.Observable

import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.model.Models.User

sealed class CampusDataUI {
    data class Success(val data : List<CampusEntity>) : CampusDataUI()
    object Error : CampusDataUI()
    data class Loading(val loading: Boolean) : CampusDataUI()
}

sealed class EventsDataUI {
    data class Success(val data : List<Event>) : EventsDataUI()
    object Error : EventsDataUI()
    data class Loading(val loading: Boolean) : EventsDataUI()
}

sealed class MyCoursesDataUI {
    data class Success(val data : List<Event>) : MyCoursesDataUI()
    object Error : MyCoursesDataUI()
    data class Loading(val loading: Boolean) : MyCoursesDataUI()
}

sealed class MyMessagesDataUI {
    data class Success(val data : List<Messages>) : MyMessagesDataUI()
    object Error : MyMessagesDataUI()
    data class Loading(val loading: Boolean) : MyMessagesDataUI()
}

sealed class UserDataUI {
    data class Sucess(val data : User) : UserDataUI()
    object Error : UserDataUI()
    data class Loading(val loading: Boolean) : UserDataUI()
}