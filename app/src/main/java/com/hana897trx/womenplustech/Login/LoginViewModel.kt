package com.hana897trx.womenplustech.Login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.common.util.Hex
import com.hana897trx.womenplustech.model.Observable.UserDataUI
import com.hana897trx.womenplustech.model.Utility.AppDB
import com.hana897trx.womenplustech.model.Utility.StateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao = AppDB.getInstance(application.applicationContext).userDao()
    private var sharedPreference = application.getSharedPreferences("remember-user", Context.MODE_PRIVATE)

    private var _userDataUIState : MutableStateFlow<UserDataUI> = MutableStateFlow(UserDataUI.Loading(true))
    val userDataUI = _userDataUIState

    init {
        tryLogin()
    }

    suspend fun checkLogin(email : String, password: String, remember : Boolean, tryLogin : Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _userDataUIState.emit(UserDataUI.Loading(true))

        val hashPassword = Hex.encodeHex(MessageDigest.getInstance("SHA-256").digest(password.toByteArray()), false)
        val response = userDao.logIn(email, if(tryLogin) password else hashPassword)

        if(response != null) {
            sharedPreference.edit().apply {
                putString("email", response.email)
                putString("password", response.password)
                putBoolean("remember", remember)
                apply()
            }
            _userDataUIState.emit(UserDataUI.Sucess(response))
        }
        else
            _userDataUIState.emit(UserDataUI.Error)
    }

    fun tryLogin() {
        if(sharedPreference != null) {
            val password = sharedPreference.getString("password", "")
            val mail = sharedPreference.getString("email", "")
            val remember = sharedPreference.getBoolean("remember", false)

            if(!password.isNullOrEmpty() && !mail.isNullOrEmpty())
                viewModelScope.launch { checkLogin(mail, password, remember, true) }
        }
    }

}