package com.hana897trx.womenplustech.model.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hana897trx.womenplustech.model.Models.Event

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun registerEvent(event : Event)

    @Query("SELECT Event.* FROM Event")
    fun getRegisterEvents() : List<Event>

    @Query("SELECT * FROM Event WHERE Event.userMail = :userMail")
    fun getRegisterEvents(userMail: String) : List<Event>
}