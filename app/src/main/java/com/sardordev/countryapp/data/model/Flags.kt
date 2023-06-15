package com.sardordev.countryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flags")
data class Flags(
    val alt: String,
    val png: String,
    val svg: String
)