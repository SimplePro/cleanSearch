package com.simplepro.cleansearch.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.simplepro.cleansearch.Instance.SearchResultInstance

class ListJsonConverter {

    @TypeConverter
    fun listToJson(value: ArrayList<SearchResultInstance>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): ArrayList<SearchResultInstance> {

        val objects = Gson().fromJson(value, Array<SearchResultInstance>::class.java) as Array<SearchResultInstance>
        val list = objects.toList()
        return list as ArrayList<SearchResultInstance>
    }
}