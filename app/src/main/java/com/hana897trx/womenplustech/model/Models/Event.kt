package com.hana897trx.womenplustech.model.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class Event(
        @PrimaryKey
    var id : String,
    var title : String,
    var description : String,
    var schedule : String,
    var campus : String,
    var days : String,
    var requirements : String,
    var registerLink : String,
    var eventImage : String,
    var temary : String,
    var eventType : String,
    var fechaInicio : Date
    )