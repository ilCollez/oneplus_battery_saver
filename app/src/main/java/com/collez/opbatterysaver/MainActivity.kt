package com.collez.opbatterysaver

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.collez.opbatterysaver.data.Utils
import com.collez.opbatterysaver.firebase.RemoteConfig
import com.collez.opbatterysaver.home.HomeContract
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity(), HomeContract.View {
    private val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    private val remoteConfig: RemoteConfig by inject()
    private val batteryListener: BatteryListener = BatteryListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.checkPermissions()
        presenter.syncViewWithSettings()

        remoteConfig.start(this) {
            presenter.checkForUpdate()
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED)

        registerReceiver(batteryListener, filter)

        applyButton.setOnClickListener { applyButtonListener() }
        switchSaving.setOnCheckedChangeListener { _, checked ->
            presenter.switchSavingListener(checked)
        }
        toggleService.setOnCheckedChangeListener { _, checked ->
            val text: String = if (checked) getString(R.string.enabled)
                else getString(R.string.disabled)

            presenter.toggleSavingListener(this, batteryListener, checked)
            toggleService.text = Utils.setEnabledSpan(
                text,
                checked
            )
        }
    }

    override fun setBatteryValue(value: Int) {
        percentageTrigger.text = Utils.setBatterySpan(
            this,
            getString(R.string.currentBatteryTrigger),
            value
        )
        Snackbar.make(
            containerMain,
            getString(R.string.valueSetSuccessfully),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun showNoPermissionDialog() {
        Utils.showDialog(
            this,
            R.string.noPermissionTitle,
            R.string.noPermissionMessage
        ) { _, _ -> finish() }
    }

    override fun showNotSupportedDialog() {
        Utils.showDialog(
            this,
            R.string.notSupportedTitle,
            R.string.notSupportedMessage
        ) { _, _ -> finish() }
    }

    override fun showUpdateDialog(url: String) {
        Utils.showDialog(
            this,
            R.string.updateRequiredTitle,
            R.string.updateRequiredMessage
        ) { _, _ -> Utils.openExternalLink(this, url) }
    }

    @SuppressLint("BatteryLife")
    override fun askBatteryOptimizationDialog() {
        Utils.showDialog(
            this,
            R.string.batteryOptTitle,
            R.string.batteryOptMessage
        ) {_, _ -> startActivity(
            Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:$packageName")
            )
        )}
    }

    override fun setButtons(
        serviceEnabled: Boolean,
        triggerOnSaving: Boolean,
        batteryPercentage: Int
    ) {
        val text: String = if (serviceEnabled) getString(R.string.enabled)
            else getString(R.string.disabled)

        toggleService.isChecked = serviceEnabled
        switchSaving.isChecked = triggerOnSaving
        percentageTrigger.text = Utils.setBatterySpan(
            this,
            getString(R.string.currentBatteryTrigger),
            batteryPercentage
        )
        toggleService.text = Utils.setEnabledSpan(
            text,
            serviceEnabled
        )
    }

    private fun applyButtonListener() {
        val textValue: String = editText.text.toString()

        if (textValue.isEmpty()) {
            Snackbar.make(containerMain, getString(R.string.noValuePassed), Snackbar.LENGTH_SHORT).show()
            return
        }

        val percentage = textValue.toInt()

        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (percentage !in 0..100) {
            Snackbar.make(containerMain, getString(R.string.outOfRange), Snackbar.LENGTH_SHORT).show()
            return
        }

        editText.text.clear()

        presenter.setBatteryPercentage(percentage)
    }
}