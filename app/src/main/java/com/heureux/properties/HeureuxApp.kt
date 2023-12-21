package com.heureux.properties

import android.app.Application
import com.heureux.properties.data.AppContainer
import com.heureux.properties.data.HeureuxAppContainer

class HeureuxApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = HeureuxAppContainer(context = this)
    }
}