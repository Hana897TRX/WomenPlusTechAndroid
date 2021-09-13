package com.hana897trx.womenplustech.model.Utility

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hana897trx.womenplustech.model.Dao.EventDao
import com.hana897trx.womenplustech.model.Dao.MessageDao
import com.hana897trx.womenplustech.model.Dao.UserDao
import com.hana897trx.womenplustech.model.Models.Event
import com.hana897trx.womenplustech.model.Models.Messages
import com.hana897trx.womenplustech.model.Models.User

@Database(entities = [Event::class, Messages::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase(){
    abstract fun eventDao(): EventDao
    abstract fun messageDao() : MessageDao
    abstract fun userDao() : UserDao
    companion object{
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context) : AppDB {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDB::class.java, "Events").build()
            }
            return INSTANCE as AppDB
        }
    }
}