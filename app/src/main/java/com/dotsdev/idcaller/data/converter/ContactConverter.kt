package com.dotsdev.idcaller.data.converter

import androidx.room.TypeConverter
import com.dotsdev.idcaller.data.model.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ContactConverter {
    @TypeConverter
    fun fromContact(value: Contact?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<Contact>() {}.type
        return value?.let {
            gson.toJson(value, type)
        }
    }

    @TypeConverter
    fun toContact(value: String?): Contact? {
        val gson = Gson()
        val type = object : TypeToken<Contact>() {}.type
        return value?.let {
            gson.fromJson(value, type)
        }
    }
}
