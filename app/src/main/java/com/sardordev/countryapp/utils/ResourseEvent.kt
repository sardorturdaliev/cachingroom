package com.sardordev.countryapp.utils



sealed class ResourceEvent<T>(data: T?, message: String? = null) {

    data class Success<T>(val data: T?) : ResourceEvent<T>(data)
    data class Error<T>(val data: T? = null, val message: String?) : ResourceEvent<T>(data, message)

}