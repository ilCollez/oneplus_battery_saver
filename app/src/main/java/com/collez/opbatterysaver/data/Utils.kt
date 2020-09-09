package com.collez.opbatterysaver.data


import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.PowerManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.collez.opbatterysaver.R


object Utils {
    private const val channelId: String = "OPBatteryNotifications"
    private const val notificationId: Int = 0

    private val supportedModels: List<String> = listOf(
        // OP Nord
        "AC2001", "AC2003",
        // OP 8
        "IN2013", "IN2017",
        // OP 7T Pro McLaren
        "HD1925",
        // OP 7T Pro
        "HD1911", "HD1913", "HD1910",
        // OP 7T
        "HD1901", "HD1903", "HD1900", "HD1907", "HD1905",
        // OP 7 Pro 5G
        "GM1925",
        // OP 7 Pro
        "GM1911", "GM1913", "GM1917", "GM1910", "GM1915"
    )

    fun hasPermissions(context: Context): Boolean {
        val writeSecureSettings = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SECURE_SETTINGS)
        return writeSecureSettings == PackageManager.PERMISSION_GRANTED
    }

    fun isBatteryOptimizationDeactivated(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun isPhoneSupported(): Boolean {
        if (Build.MANUFACTURER != "OnePlus")
            return false
        if (!supportedModels.contains(Build.MODEL))
            return false

        return true
    }

    fun setBatterySpan(ctx: Context, str: String, value: Int): SpannableStringBuilder {
        val text = str.format(value)

        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.textBox)), 0, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.RED), text.length - value.toString().length,
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
    }

    fun showDialog(ctx: Context, title: Int, message: Int, unit: (DialogInterface, Int) -> Unit) {
        AlertDialog.Builder(ctx, R.style.alertDialog).run {
            setTitle(title)
            setMessage(message)
            create()
            setPositiveButton(context.getString(android.R.string.ok), unit)
            show()
        }
    }

    fun showNotification(ctx: Context, title: String, text: String) {
        val mChannel = NotificationChannel(channelId, ctx.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH)
        val mNotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.createNotificationChannel(mChannel)

        val builder = NotificationCompat.Builder(ctx, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_notification)
            setContentTitle(title)
            setContentText(text)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        mNotificationManager.notify(notificationId, builder.build())
    }

    fun setEnabledSpan(text: String, serviceEnabled: Boolean): SpannableStringBuilder {
        val backgrounColor = if (serviceEnabled) Color.GREEN
            else Color.RED

        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(ForegroundColorSpan(backgrounColor), 0, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        return spannable
    }
}