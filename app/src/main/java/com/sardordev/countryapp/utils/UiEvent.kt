package com.sardordev.countryapp.utils

sealed class UiEvent {

    data class Success<T>(val  data: T): UiEvent()
    data class Error(val message:String) : UiEvent()
    object Loading : UiEvent()
    object Empty : UiEvent()


}