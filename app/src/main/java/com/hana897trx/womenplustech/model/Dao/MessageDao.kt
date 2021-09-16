package com.hana897trx.womenplustech.model.Dao

import androidx.room.*
import com.hana897trx.womenplustech.model.Models.Messages

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun InsertMessage(message : Messages)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessages(messages: List<Messages>)

    @Query("SELECT * FROM Messages WHERE idEvent = :idEvent")
    fun getMessage(idEvent : String) : List<Messages>

    @Query("Update messages SET seen_at = 1 WHERE seen_at = 0 and idEvent = :idEvent")
    fun updateSeenAt(idEvent: String)

    @Query("SELECT COUNT(*) FROM messages WHERE seen_at != 1 and idEvent = :idEvent")
    fun countNotSeen(idEvent: String) : Int

    @Delete
    fun deleteMessage(message: Messages)
}