package com.hana897trx.womenplustech.model.Observable

import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Models.Event

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