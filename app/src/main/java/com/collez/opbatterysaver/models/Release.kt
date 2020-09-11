package com.collez.opbatterysaver.models


import com.collez.opbatterysaver.BuildConfig
import kotlinx.serialization.Serializable

@Serializable
data class Release (
    val version: String = BuildConfig.VERSION_NAME,
    val buildNumber: Int = BuildConfig.VERSION_CODE,
    val downloadUrl: String = "https://github.com/ilCollez/oneplus_battery_saver/releases"
)