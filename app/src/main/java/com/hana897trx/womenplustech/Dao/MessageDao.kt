package com.hana897trx.womenplustech.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hana897trx.womenplustech.Models.Messages

@Dao
interface MessageDao {
    @Insert
    fun InsertMessage(message : Messages)

    @Query("SELECT * FROM Messages WHERE event_id = :event_id")
    fun getMessage(event_id : String) : List<Messages>

    @Delete
    fun deleteMessage(message: Messages)
}