package com.sardordev.countryapp.utils

import android.content.Context
import android.widget.Toast

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}