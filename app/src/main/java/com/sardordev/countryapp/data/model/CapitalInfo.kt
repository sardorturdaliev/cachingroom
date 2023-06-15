package com.sardordev.countryapp.data.model

import androidx.room.Entity

@Entity
data class CapitalInfo(
    val latlng: List<Double>
)