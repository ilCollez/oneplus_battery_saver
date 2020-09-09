package com.collez.opbatterysaver.home

import android.content.Context
import com.collez.opbatterysaver.BatteryListener
import com.collez.opbatterysaver.data.Settings
import com.collez.opbatterysaver.data.Utils
import org.koin.core.KoinComponent
import org.koin.core.inject


class HomePresenter(private val view: HomeContract.View, private val ctx: Context) : HomeContract.Presenter, KoinComponent {
    private val settings: Settings by inject()

    override fun setBatteryPercentage(percentage: Int) {
        settings.batteryPercentage = percentage
        view.setBatteryValue(percentage)
    }

    override fun switchSavingListener(isChecked: Boolean) {
        settings.triggerOnSaving = isChecked
    }

    override fun toggleSavingListener(context: Context, listener: BatteryListener, isChecked: Boolean) {
        settings.serviceEnabled = isChecked


        //TODO sistemare errore sulla riga 35 (broadcast not registered)
        /*
        if (isChecked) {
            val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)

            ctx.registerReceiver(listener, filter)
        }
        else
            ctx.unregisterReceiver(listener)
        */
    }

    override fun checkPermissions() {
        if (Utils.isPhoneSupported()) {
            if (Utils.hasPermissions(ctx)) {
                if (!Utils.isBatteryOptimizationDeactivated(ctx))
                    view.askBatteryOptimizationDialog()
            }
            else
                view.showNoPermissionDialog()
        }
        else
            view.showNotSupportedDialog()
    }

    override fun syncViewWithSettings() {
        view.setButtons(
            settings.serviceEnabled,
            settings.triggerOnSaving,
            settings.batteryPercentage
        )
    }
}