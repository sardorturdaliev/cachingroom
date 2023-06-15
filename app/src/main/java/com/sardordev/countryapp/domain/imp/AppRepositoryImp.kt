package com.sardordev.countryapp.domain.imp

import androidx.room.withTransaction
import com.sardordev.countryapp.data.api.CountryApi
import com.sardordev.countryapp.data.dt.db.CountryDatabase
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.domain.repository.AppRepository
import com.sardordev.countryapp.utils.ResourceEvent
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(
    private val api: CountryApi
) : AppRepository {


    override suspend fun getAllCountry(): ResourceEvent<List<CountryData>> {
        return try {
            val result = api.getAllCountry()
            if (result.isSuccessful) {
                ResourceEvent.Success(result.body())
            } else {
                ResourceEvent.Error(message = result.message())
            }
        } catch (e: Exception) {
            ResourceEvent.Error(message = e.message)
        }
    }



}