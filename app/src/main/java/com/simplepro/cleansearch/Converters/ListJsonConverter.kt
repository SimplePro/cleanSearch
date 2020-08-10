package com.simplepro.cleansearch.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.simplepro.cleansearch.CustomClass.SearchResultCustomClass

class ListJsonConverter {

    @TypeConverter
    fun listToJson(value: ArrayList<SearchResultCustomClass>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): ArrayList<SearchResultCustomClass> {

        val objects = Gson().fromJson(value, Array<SearchResultCustomClass>::class.java) as Array<SearchResultCustomClass>
        val list = objects.toList()
        return list as ArrayList<SearchResultCustomClass>
    }
}