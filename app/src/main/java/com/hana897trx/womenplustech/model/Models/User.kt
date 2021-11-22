package com.hana897trx.womenplustech.model.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
data class User (
    @PrimaryKey
    val id : Long = 0,

    val userName : String? = null,

    val birthDate : Date? = null,

    val email : String? = null,

    val password : String? = null,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imgProfile : ByteArray,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imgCover : ByteArray? = null
    )
