package com.hana897trx.womenplustech.model.Observable

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.hana897trx.womenplustech.model.API.APIEvents
import com.hana897trx.womenplustech.model.Models.Event
import org.json.JSONArray

class EventsObservable() : BaseObservable() {
    private var api : APIEvents

    init {
        api = APIEvents()
    }

    /*
    fun getCampus() : MutableLiveData<JSONArray> {
        return api.getCampus()
    }*/
}