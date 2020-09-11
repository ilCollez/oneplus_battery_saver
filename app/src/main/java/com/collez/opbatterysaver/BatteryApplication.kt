package com.collez.opbatterysaver

import android.app.Application
import com.collez.opbatterysaver.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BatteryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BatteryApplication)
            modules(koinModules)
        }
    }
}