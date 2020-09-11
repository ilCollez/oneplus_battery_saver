package com.collez.opbatterysaver.di

import com.collez.opbatterysaver.data.Settings
import com.collez.opbatterysaver.firebase.RemoteConfig
import com.collez.opbatterysaver.home.HomeContract
import com.collez.opbatterysaver.home.HomePresenter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModule = module {
    factory<HomeContract.Presenter> { (view: HomeContract.View) -> HomePresenter(view, androidApplication()) }
}

val dataModule = module {
    single { Settings(androidContext()) }
    single { RemoteConfig() }
}


val koinModules = arrayListOf(dataModule, viewModule)