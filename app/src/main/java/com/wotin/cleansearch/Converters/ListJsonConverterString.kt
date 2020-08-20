package com.wotin.cleansearch.Converters

import com.google.gson.Gson

class ListJsonConverterString {

    fun listToJson(value : ArrayList<String>) : String = Gson().toJson(value)

}