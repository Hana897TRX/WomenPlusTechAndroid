package com.hana897trx.womenplustech.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hana897trx.womenplustech.Models.Event

@Dao
interface EventDao {
    @Insert
    fun registerEvent(event : Event)

    @Query("SELECT * FROM Event")
    fun getRegisterEvents() : List<Event>
}