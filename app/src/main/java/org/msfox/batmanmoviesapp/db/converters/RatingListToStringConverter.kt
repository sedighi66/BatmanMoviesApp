package org.msfox.batmanmoviesapp.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.msfox.batmanmoviesapp.model.Rating
import java.lang.reflect.Type


class RatingListToStringConverter {

    @TypeConverter
    fun stringToList(value: String): List<Rating> {
        val listType: Type = object : TypeToken<List<Rating>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Rating>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}