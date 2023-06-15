package com.sardordev.countryapp.data.dt.enity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sardordev.countryapp.data.model.Flags
import com.sardordev.countryapp.data.model.Name
import com.sardordev.countryapp.data.model.NativeName

class NativeNameConverter {
    private val gson = Gson()


    @TypeConverter
    fun fromNativeName(nativeName: NativeName): String {
        return gson.toJson(nativeName)
    }
    @TypeConverter
    fun toNativeName(json: String): NativeName {
        return gson.fromJson(json, NativeName::class.java)
    }



    @TypeConverter
    fun fromFlags(flags: Flags): String {
        return gson.toJson(flags)
    }
    @TypeConverter
    fun toFlags(json: String): Flags {
        return gson.fromJson(json, Flags::class.java)
    }



    @TypeConverter
    fun fromName(name: Name): String {
        return gson.toJson(name)
    }
    @TypeConverter
    fun toName(json: String): Name {
        return gson.fromJson(json, Name::class.java)
    }


}