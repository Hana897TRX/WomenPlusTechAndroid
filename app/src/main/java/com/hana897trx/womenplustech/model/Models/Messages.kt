package com.hana897trx.womenplustech.model.Models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
class Messages(
    @PrimaryKey
    var _id: String,
    var idEvent: String,
    var message: String,
    var fecha_envio: Date,
    var seen_at: Boolean
)