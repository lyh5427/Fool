package com.villains.fool

import android.content.Context
import android.content.SharedPreferences
import com.villains.fool.presentation.Const

class Prefs(context: Context) {
    val PREFS_FILENAME = "com.datauniverse.antiscam.prefs"

    val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_FILENAME,
        Context.MODE_PRIVATE
    )

    var accessToken: String
        get() = prefs.getString("AccessToken", "")!!
        set(value) = prefs.edit().putString("AccessToken", value).apply()

    var snsId: String
        get() = prefs.getString("SnsId", "")!!
        set(value) = prefs.edit().putString("SnsId", value).apply()

    var isLogin: Boolean
        get() = prefs.getBoolean("LoginFlag", false)
        set(value) = prefs.edit().putBoolean("LoginFlag", value).apply()

    var baseCountry: String
        get() = prefs.getString("CountryCode", "")!!
        set(value) = prefs.edit().putString("CountryCode", value).apply()

    var exchangeCountry: String
        get() = prefs.getString("ExchageCountry", "")!!
        set(value) = prefs.edit().putString("ExchageCountry", value).apply()

    var idToken: String
        get() = prefs.getString("IdToken", "")!!
        set(value) = prefs.edit().putString("IdToken", value).apply()

    var exchangeRate: HashMap<String, Double> = HashMap()

    val currencyToCountry: HashMap<String, String> = HashMap<String, String>()
        .apply {
            set("AU", Const.AUSTRALIA)  // 호주 달러 -> AU (Australia)
            set("CN", Const.CHINA)      // 위안화 -> CN (China)
            set("EU", Const.EUROPE)     // 유로 -> EU (European Union)
            set("JP", Const.JAPAN)      // 일본 옌 -> JP (Japan)
            set("KR", Const.KOREA)      // 한국 원 -> KR (South Korea)
            set("US", Const.USA)        // 미국 달러 -> US (United States)
            set("SG", Const.SINGAPORE)  // 싱가포르 달러 -> SG (Singapore)
            set("TH", Const.THAILAND)   // 태국 바트 -> TH (Thailand)
        }
}