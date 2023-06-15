package com.sardordev.countryapp.app

import android.app.Application
import com.sardordev.countryapp.data.dt.db.CountryDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CountryDatabase.init(this)
    }
}