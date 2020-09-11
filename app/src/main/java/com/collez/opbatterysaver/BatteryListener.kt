package com.collez.opbatterysaver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.PowerManager
import com.collez.opbatterysaver.data.*
import com.collez.opbatterysaver.models.RefreshRate
import org.koin.core.KoinComponent
import org.koin.core.inject


class BatteryListener : BroadcastReceiver(), KoinComponent {
    private val settings: Settings by inject()

    override fun onReceive(p0: Context?, p1: Intent?) {

        //TODO rimpiazzare ciò il prima possibile con una deregistrazione del broadcast
        if (!settings.serviceEnabled)
            return

        if (p0 != null && p1 != null) {
            val powerManager: PowerManager = p0.getSystemService(Context.POWER_SERVICE) as PowerManager
            val triggerOnSaving: Boolean = settings.triggerOnSaving
            val refreshRate: RefreshRate = settings.opRefreshRate

            when (p1.action) {
                Intent.ACTION_BATTERY_CHANGED -> {
                    val percentage: Int = p1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    val status: Int = p1.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                    val savedValue: Int = settings.batteryPercentage
                    val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL

                    if (percentage <= savedValue && !isCharging && refreshRate == RefreshRate.Hz90) {
                        settings.opRefreshRate = RefreshRate.Hz60
                        showRateChange(p0, RefreshRate.Hz60)
                    }

                    else if (isCharging && refreshRate == RefreshRate.Hz60) {
                        settings.opRefreshRate = RefreshRate.Hz90
                        showRateChange(p0, RefreshRate.Hz90)
                    }
                }

                PowerManager.ACTION_POWER_SAVE_MODE_CHANGED -> {
                    if (!triggerOnSaving)
                        return

                    if (powerManager.isPowerSaveMode && refreshRate == RefreshRate.Hz90) {
                        settings.opRefreshRate = RefreshRate.Hz60
                        showRateChange(p0, RefreshRate.Hz60)
                    }

                    else if (!powerManager.isPowerSaveMode && refreshRate == RefreshRate.Hz60) {
                        settings.opRefreshRate = RefreshRate.Hz90
                        showRateChange(p0, RefreshRate.Hz90)
                    }
                }
            }
        }
    }

    private fun showRateChange(p0: Context, rate: RefreshRate) {
        Utils.showNotification(p0,
            p0.getString(R.string.refreshRateChangedTitle),
            p0.getString(R.string.refreshRateChangedMessage, rate.toText())
        )
    }
}