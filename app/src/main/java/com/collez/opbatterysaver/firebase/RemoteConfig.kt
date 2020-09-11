package com.collez.opbatterysaver.firebase


import android.app.Activity
import com.collez.opbatterysaver.data.Utils
import com.collez.opbatterysaver.models.Release
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RemoteConfig {
    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    private val defaultSettings = mapOf(
        "latestRelease" to Json.encodeToString(Release())
    )

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(defaultSettings)
    }

    lateinit var latestRelease: Release

    fun start (activity: Activity, cb: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            runCatching { remoteConfig.fetchAndActivate().await() }
                .onSuccess {
                    latestRelease = Json.decodeFromString(remoteConfig.getString("latestRelease"))
                    launch(Dispatchers.Main) {
                        cb.invoke()
                    }
                }
                .onFailure {
                    launch(Dispatchers.Main) {
                        Utils.showNoServiceDialog(activity) {_, _ -> start(activity, cb)}
                    }
                }
        }
    }

}