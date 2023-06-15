package com.sardordev.countryapp.data.dt.db

import android.content.Context
import androidx.room.*
import com.sardordev.countryapp.data.dt.db.dao.CountryDao
import com.sardordev.countryapp.data.dt.enity.CountryEntity
import com.sardordev.countryapp.data.dt.enity.NativeNameConverter
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.data.model.Flags
import com.sardordev.countryapp.data.model.Name

@Database([CountryData::class], version = 1)
@TypeConverters(NativeNameConverter::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun getcountryDao(): CountryDao


    companion object {
        private var instance: CountryDatabase? = null
        fun init(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CountryDatabase::class.java, "countrydbb")
                    .allowMainThreadQueries().build()
            }
        }
        fun getInstance() = instance!!
    }



}