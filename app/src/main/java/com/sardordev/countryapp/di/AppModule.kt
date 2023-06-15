package com.sardordev.countryapp.di

import com.sardordev.countryapp.data.api.CountryApi
import com.sardordev.countryapp.data.dt.db.CountryDatabase
import com.sardordev.countryapp.domain.imp.AppRepositoryImp
import com.sardordev.countryapp.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountryApi(): CountryApi {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }


    @Singleton
    @Provides
    fun provideAppRepository(countryApi: CountryApi): AppRepository {
        return AppRepositoryImp(countryApi)
    }


    @Singleton
    @Provides
    fun prodiveCountryDatabase():CountryDatabase{
        return CountryDatabase.getInstance()
    }


}