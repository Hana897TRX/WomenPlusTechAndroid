package com.hana897trx.womenplustech.model.Observable

import com.hana897trx.womenplustech.model.Models.CampusEntity

sealed class CampusDataUI {
    data class Success(val data : List<CampusEntity>) : CampusDataUI()
    object Error : CampusDataUI()
    data class Loading(val loading: Boolean) : CampusDataUI()
}