package com.collez.opbatterysaver.home

import android.content.Context
import com.collez.opbatterysaver.BatteryListener

interface HomeContract {

    interface View {
        fun setBatteryValue(value: Int)
        fun showNoPermissionDialog()
        fun showNotSupportedDialog()
        fun askBatteryOptimizationDialog()
        fun setButtons(serviceEnabled: Boolean, triggerOnSaving: Boolean, batteryPercentage: Int)
    }

    interface Presenter {
        fun setBatteryPercentage(percentage: Int)
        fun switchSavingListener(isChecked: Boolean)
        fun toggleSavingListener(context: Context, listener: BatteryListener, isChecked: Boolean)
        fun checkPermissions()
        fun syncViewWithSettings()
    }

}