package com.sardordev.countryapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sardordev.countryapp.data.dt.enity.NativeNameConverter

@Entity(tableName = "countryData")
data class CountryData(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
//    val altSpellings: List<String>,
//    val borders: List<String>,
//    val capital: List<String>,
//    val capitalInfo: CapitalInfo,
//    val car: Car,
//    val cca2: String,
//    val ccn3: String,
//    val cioc: String,
//    val coatOfArms: CoatOfArms,
//    val continents: List<String>,
//    val currencies: Currencies,
//    val demonyms: Demonyms,
//    val fifa: String,
//    val flag: String,
    val flags: Flags?=null,
//    val gini: Gini,
//    val idd: Idd,
    val independent: Boolean = false,
//    val landlocked: Boolean,
//    val languages: Languages,
//    val latlng: List<Double>,
//    val maps: Maps,
    val name: Name?=null,
    val population: Int ?=null,
//    val postalCode: PostalCode,
//    @ColumnInfo(name = "region")
    val region: String?=null,
    val startOfWeek: String = "",
//    val status: String,
    val subregion: String?=null
//    val timezones: List<String>,
//    val tld: List<String>,
//    val translations: Translations,
//    val unMember: Boolean
)