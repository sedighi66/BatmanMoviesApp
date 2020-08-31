package org.msfox.batmanmoviesapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class StringListToStringConverter {

    @TypeConverter
    fun stringToList(value: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}