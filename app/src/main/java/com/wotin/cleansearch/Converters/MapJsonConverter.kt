package com.wotin.cleansearch.Converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapJsonConverter {

    fun MapToJsonConverter(json: String): Map<String, Any> {

        val gson = Gson()

        var Map: Map<String, Any> =
            gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
        Map.forEach { println(it) }

        return Map
    }

}
