package com.sardordev.countryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name")
data class Name(
    val common: String,
    val nativeName: NativeName,
    val official: String
)