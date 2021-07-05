package com.hana897trx.womenplustech.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
class Messages (
    @PrimaryKey
    var _id : String,
    var event_id : String,
    var message : String,
    var fecha_envio : Date,
)