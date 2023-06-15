package com.sardordev.countryapp.data.api

import com.sardordev.countryapp.data.model.CountryData
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {

    @GET("all")
    suspend fun getAllCountry(): Response<List<CountryData>>

}