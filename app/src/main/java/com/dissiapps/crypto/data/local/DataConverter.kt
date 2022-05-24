package com.dissiapps.crypto.data.local

import androidx.room.TypeConverter
import com.dissiapps.crypto.data.models.news.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromCurrenciesList(value: List<Currency>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Currency>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCurrenciesList(value: String): List<Currency>? {
        val gson = Gson()
        val type = object : TypeToken<List<Currency>>() {}.type
        return gson.fromJson(value, type)
    }
}