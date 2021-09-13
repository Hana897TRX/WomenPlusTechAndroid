package com.hana897trx.womenplustech.model.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hana897trx.womenplustech.model.Models.Messages

@Dao
interface MessageDao {
    @Insert
    fun InsertMessage(message : Messages)

    @Query("SELECT * FROM Messages WHERE idEvent = :idEvent")
    fun getMessage(idEvent : String) : List<Messages>

    @Delete
    fun deleteMessage(message: Messages)
}