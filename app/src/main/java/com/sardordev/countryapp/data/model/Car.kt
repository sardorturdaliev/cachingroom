package com.sardordev.countryapp.data.model

import androidx.room.Entity

@Entity
data class Car(
    val side: String,
    val signs: List<String>
)