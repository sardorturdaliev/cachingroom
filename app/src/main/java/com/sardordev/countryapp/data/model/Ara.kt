package com.sardordev.countryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ara")
data class Ara(
    val common: String,
    val official: String
)