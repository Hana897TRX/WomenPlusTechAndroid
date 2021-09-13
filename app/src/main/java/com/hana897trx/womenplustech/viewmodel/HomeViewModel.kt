package com.hana897trx.womenplustech.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.EventsObservable
import org.json.JSONArray

class HomeViewModel : ViewModel() {
    private var campus : MutableLiveData<JSONArray>
    private var events : MutableLiveData<List<Event>>
    private var eventObservable : EventsObservable

    init {
        eventObservable = EventsObservable()
        campus = MutableLiveData<JSONArray>()
        events = MutableLiveData<List<Event>>()
    }

    fun getCampus() : MutableLiveData<JSONArray> {
        campus = eventObservable.getCampus()
        return campus
    }

    fun getEvents() : MutableLiveData<List<Event>> {
        events = eventObservable.getEvents()
        return events
    }
}