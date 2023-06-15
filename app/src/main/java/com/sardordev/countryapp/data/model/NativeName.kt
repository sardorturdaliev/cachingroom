package com.sardordev.countryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nativeName")
data class NativeName(
    val ara: Ara
)