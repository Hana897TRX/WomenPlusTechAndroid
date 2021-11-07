package com.hana897trx.womenplustech.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.CampusDataUI
import com.hana897trx.womenplustech.model.Observable.EventsObservable
import com.hana897trx.womenplustech.model.Utility.StateResult
import com.hana897trx.womenplustech.model.usecases.CampusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class HomeViewModel() : ViewModel() {
    private var campus : MutableLiveData<JSONArray>
    private var events : MutableLiveData<List<Event>>
    private var eventObservable : EventsObservable
    private val campusUseCase = CampusUseCase()

    private val _campusUIState : MutableStateFlow<CampusDataUI> = MutableStateFlow(CampusDataUI.Loading(true))
    val campusDataUI = _campusUIState

    init {
        getCampusData()
        eventObservable = EventsObservable()
        campus = MutableLiveData<JSONArray>()
        events = MutableLiveData<List<Event>>()
    }

    private fun getCampusData() = viewModelScope.launch {
        when(val response = campusUseCase.invoke()) {
            is StateResult.Success -> _campusUIState.emit(CampusDataUI.Success(response.data))
            is StateResult.Loading -> _campusUIState.emit(CampusDataUI.Loading(response.loading))
            is StateResult.Error -> _campusUIState.emit(CampusDataUI.Error)
        }
    }

    fun getCampus() : MutableLiveData<JSONArray> {
        //campus = eventObservable.getCampus()
        return campus
    }

    fun getEvents() : MutableLiveData<List<Event>> {
        events = eventObservable.getEvents()
        return events
    }
}