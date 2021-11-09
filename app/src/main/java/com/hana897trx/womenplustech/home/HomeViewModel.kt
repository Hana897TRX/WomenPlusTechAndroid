package com.hana897trx.womenplustech.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.API.APIEvents
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.CampusDataUI
import com.hana897trx.womenplustech.model.Observable.EventsDataUI
import com.hana897trx.womenplustech.model.Observable.EventsObservable
import com.hana897trx.womenplustech.model.Utility.StateResult
import com.hana897trx.womenplustech.model.usecases.CampusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class HomeViewModel() : ViewModel() {
    private val api = APIEvents()

    private val _eventsUIState : MutableStateFlow<EventsDataUI> = MutableStateFlow(EventsDataUI.Loading(true))
    val eventsUIState = _eventsUIState

    private val _campusUIState : MutableStateFlow<CampusDataUI> = MutableStateFlow(CampusDataUI.Loading(true))
    val campusDataUI : StateFlow<CampusDataUI> = _campusUIState

    init {
        getCampusData()
        getEventsData()
    }

    private fun getCampusData() = viewModelScope.launch {
        when(val response = api.getCampus()) {
            is StateResult.Success -> _campusUIState.emit(CampusDataUI.Success(response.data))
            is StateResult.Loading -> _campusUIState.emit(CampusDataUI.Loading(response.loading))
            is StateResult.Error -> _campusUIState.emit(CampusDataUI.Error)
        }
    }

    internal fun getEventsData() = viewModelScope.launch {
        when(val response = api.getEvents()) {
            is StateResult.Success -> _eventsUIState.emit(EventsDataUI.Success(response.data))
            is StateResult.Loading -> _eventsUIState.emit(EventsDataUI.Loading(response.loading))
            is StateResult.Error -> _eventsUIState.emit(EventsDataUI.Error)
        }
    }
}