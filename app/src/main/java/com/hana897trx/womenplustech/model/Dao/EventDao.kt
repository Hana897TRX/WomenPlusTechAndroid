package com.hana897trx.womenplustech.model.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hana897trx.womenplustech.model.Models.Event

@Dao
interface EventDao {
    @Insert
    fun registerEvent(event : Event)

    @Query("SELECT * FROM Event")
    fun getRegisterEvents() : List<Event>
}