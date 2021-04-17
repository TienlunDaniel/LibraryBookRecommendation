package com.example.librarybookrecommendation.database

import androidx.room.TypeConverter
import com.example.librarybookrecommendation.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BookConverter {
    @TypeConverter
    fun fromStringList(strList: List<String>): String {
        return strList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(plainString: String): List<String> {
        return plainString.split(",").toList()
    }
}