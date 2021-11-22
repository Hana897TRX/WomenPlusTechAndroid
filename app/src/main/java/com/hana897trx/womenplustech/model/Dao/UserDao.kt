package com.hana897trx.womenplustech.model.Dao

import androidx.room.*
import com.hana897trx.womenplustech.model.Models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE User.email = :email and User.password = :password")
    fun logIn(email : String, password : String) : User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun newUser(user: User)

    @Update
    fun updateUser(user : User)

    @Query("SELECT * FROM User")
    fun getUsers() : List<User>

    @Query("SELECT User.id, User.email, User.userName, User.birthDate, User.imgProfile, User.imgCover, User.birthDate FROM User WHERE User.id = :id")
    fun getUser(id : Long) : User

    @Query("SELECT User.id, User.email, User.userName, User.birthDate, User.imgProfile, User.imgCover, User.birthDate FROM User WHERE User.email = :email")
    fun getUser(email : String) : User

    @Delete
    fun deleteUser(user : User)

}