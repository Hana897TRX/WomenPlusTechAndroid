package com.hana897trx.womenplustech.model.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class User (
    @PrimaryKey
    val id : Long,

    val userName : String,

    val birthDate : Date,

    val email : String,

    val password : String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imgProfile : ByteArray,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imgCover : ByteArray
    )
