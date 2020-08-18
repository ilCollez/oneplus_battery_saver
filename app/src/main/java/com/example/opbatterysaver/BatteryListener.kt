package com.example.opbatterysaver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.provider.Settings

class BatteryListener : BroadcastReceiver() {
    private var previousPercentage: Int? = null

    override fun onReceive(p0: Context?, p1: Intent?) {
        val percentage: Int = p1?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 0

        if (previousPercentage == null)
            previousPercentage = percentage

        if (percentage != previousPercentage) {
            val status: Int = p1?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val savedValue: Int? = p0?.getSharedPreferences("localValues", Context.MODE_PRIVATE)?.getInt("batteryPercentage", 25)
            val refreshRateValue: String = Settings.Global.getInt(p0?.contentResolver, "oneplus_screen_refresh_rate").toString()
            val refreshState: RefreshRate = RefreshRate.fromInt(refreshRateValue.toInt())
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL

            if (isCharging && refreshState == RefreshRate.Hz60) {
                setRefreshRate(p0, RefreshRate.Hz90)
                return
            }


            if (percentage == savedValue && !isCharging)
                setRefreshRate(p0, RefreshRate.Hz60)


            previousPercentage = percentage
        }
    }

    private fun setRefreshRate(p0: Context?, rate: RefreshRate) {
        Settings.Global.putInt(p0?.contentResolver, "oneplus_screen_refresh_rate", rate.num)
    }

    private enum class RefreshRate (val num: Int) {
        Hz60(1),
        Hz90 (2);

        companion object {
            fun fromInt(value: Int) = values().first { it.num == value }
        }
    }
}