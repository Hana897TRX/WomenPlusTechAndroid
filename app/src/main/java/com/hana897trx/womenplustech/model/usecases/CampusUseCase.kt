package com.hana897trx.womenplustech.model.usecases

import com.hana897trx.womenplustech.model.API.APIEvents
import com.hana897trx.womenplustech.model.Models.CampusEntity
import com.hana897trx.womenplustech.model.Utility.StateResult
import java.lang.Exception

class CampusUseCase (
        private val apiEvents: APIEvents = APIEvents()
    ) {

    suspend fun invoke() : StateResult<List<CampusEntity>> {
        try {
            return apiEvents.getCampus()
        }
        catch (e : Exception) {
            throw e
        }
    }
}