package com.hana897trx.womenplustech.Utility

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hana897trx.womenplustech.Dao.EventDao
import com.hana897trx.womenplustech.Models.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase(){
    abstract fun eventDao(): EventDao
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