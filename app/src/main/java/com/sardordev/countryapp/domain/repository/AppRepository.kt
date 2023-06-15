package com.sardordev.countryapp.domain.repository

import com.sardordev.countryapp.data.dt.enity.CountryEntity
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.utils.ResourceEvent

interface AppRepository {
    suspend fun getAllCountry(): ResourceEvent<List<CountryData>>
}