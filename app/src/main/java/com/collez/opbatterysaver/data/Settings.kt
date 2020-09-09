package com.collez.opbatterysaver.data

import android.content.Context

class Settings(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(STORAGE_PREF, Context.MODE_PRIVATE)

    companion object {
        private const val STORAGE_PREF = "STORAGE_PREF"
        private const val SERVICE_ENABLED_KEY: String = "SERVICE_ENABLED_KEY"
        private const val BATTERY_PERCENTAGE_KEY: String = "BATTERY_PERCENTAGE_KEY"
        private const val TRIGGER_ON_SAVING_KEY: String = "TRIGGER_ON_SAVING_KEY"
        private const val ONEPLUS_SCREEN_REFRESH_RATE: String = "oneplus_screen_refresh_rate"
    }

    var opRefreshRate: RefreshRate = RefreshRate.Hz60
        get() = RefreshRate.fromInt(android.provider.Settings.Global
                    .getInt(context.contentResolver, ONEPLUS_SCREEN_REFRESH_RATE))
        set(value) {
            android.provider.Settings.Global.putInt(context.contentResolver, ONEPLUS_SCREEN_REFRESH_RATE, value.num)
            field = value
        }

    var serviceEnabled: Boolean = false
        get() = sharedPreferences.getBoolean(SERVICE_ENABLED_KEY, false)
        set(value) {
            sharedPreferences.edit()
                .putBoolean(SERVICE_ENABLED_KEY, value).apply()
            field = value
            println(value)
        }


    var batteryPercentage: Int = 0
        get() = sharedPreferences.getInt(BATTERY_PERCENTAGE_KEY, 0)
        set(value) {
            sharedPreferences.edit()
                .putInt(BATTERY_PERCENTAGE_KEY, value).apply()
            field = value
            println(value)
        }



    var triggerOnSaving: Boolean = false
        get() = sharedPreferences.getBoolean(TRIGGER_ON_SAVING_KEY, false)
        set(value) {
            sharedPreferences.edit()
                .putBoolean(TRIGGER_ON_SAVING_KEY, value).apply()
            field = value
            println(value)
        }
}