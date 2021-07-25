package com.hana897trx.womenplustech.Dao

import androidx.room.*
import com.hana897trx.womenplustech.Models.User

@Dao
interface UserDao {
    @Insert
    fun newUser(user: User)

    @Update
    fun updateUser(user : User)

    @Query("SELECT * FROM User")
    fun getUser() : List<User>

    @Delete
    fun deleteUser(user : User)

}