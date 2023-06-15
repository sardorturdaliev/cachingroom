package com.sardordev.countryapp.data.dt.enity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val countryName:String,
    val imgurl:String
)



