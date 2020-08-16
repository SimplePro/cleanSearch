package com.wotin.cleansearch.StringCount


class StringCount {
    fun stringCount(text : String, word : String): Int {
        var count = 0
        var fromIndex = -1
        while (text.indexOf(word, fromIndex + 1).also { fromIndex = it } >= 0) {
            count++
        }

        return count
    }
}