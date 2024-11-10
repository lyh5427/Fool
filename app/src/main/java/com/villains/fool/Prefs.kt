package com.villains.fool

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.datauniverse.antiscam.prefs"

    val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_FILENAME,
        Context.MODE_PRIVATE
    )

    var baseCountry: String
        get() = prefs.getString("CountryCode", "")!!
        set(value) = prefs.edit().putString("CountryCode", value).apply()

    var exchangeCountry: String
        get() = prefs.getString("ExchageCountry", "")!!
        set(value) = prefs.edit().putString("ExchageCountry", value).apply()

    var lastServiceCode: String
        get() = prefs.getString("lastServiceCode", "")!!
        set(value) = prefs.edit().putString("lastServiceCode", value).apply()

}