package com.hana897trx.womenplustech.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Observable.EventsObservable
import org.json.JSONArray
import android.content.DialogInterface

class HomeViewModel() : ViewModel() {
    private var campus : MutableLiveData<JSONArray>
    private var events : MutableLiveData<List<Event>>
    private var eventObservable : EventsObservable

    /*
    companion object {
        @JvmStatic
        @BindingAdapter("click")
        fun setName(card : CardView) {
            var text = ""
            val builder = AlertDialog.Builder(card.context)
            builder.setTitle("¿Cuál es tu nombre?")
            val input = EditText(card.context)
            builder.setView(input)

            builder.setPositiveButton("OK") { dialog, which ->
                text = input.text.toString()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }
    }
     */

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