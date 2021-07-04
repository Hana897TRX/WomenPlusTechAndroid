package com.hana897trx.womenplustech.Utility

import androidx.room.TypeConverter
import java.sql.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value : Long?) : Date? {
        return value?.let{ Date(it) }
    }

    @TypeConverter
    fun ateToTimestamp(date : Date?) : Long? {
        return date?.time
    }
}