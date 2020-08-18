package com.example.opbatterysaver

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(BatteryListener(), IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        applyButton.setOnClickListener {
            val percentage = editText.text.toString().toInt()
            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            if (percentage !in 0..100) {
                Snackbar.make(containerMain, getString(R.string.outOfRange), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            getSharedPreferences("localValues", Context.MODE_PRIVATE).edit().apply {
                putInt("batteryPercentage", percentage)
            }.apply()
            Snackbar.make(containerMain, getString(R.string.valueSetSuccessfully), Snackbar.LENGTH_SHORT).show()
        }
        switchSaving.setOnCheckedChangeListener { _, isChecked ->
            getSharedPreferences("localValues", Context.MODE_PRIVATE).edit().apply {
                putBoolean("triggerOnEnergySaving", isChecked)
            }.apply()
        }
    }

    override fun onStart() {
        super.onStart()
        val writeSecureSettings = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_SECURE_SETTINGS)

        if (writeSecureSettings == PackageManager.PERMISSION_DENIED) {
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setTitle(R.string.noPermissionTitle)
                setMessage(R.string.noPermissionMessage)
                create()
                setPositiveButton(getString(android.R.string.ok)) { _, _ -> finish() }
                show()
            }

        }
    }
}