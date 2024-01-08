package com.heureux.properties

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.heureux.properties.data.AppContainer
import com.heureux.properties.data.HeureuxAppContainer
import com.heureux.properties.data.repositories.UserPreferencesRepository

private const val USER_PREFERENCE_NAME = "user_preferences"
val Context.user_dataStore by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

class HeureuxApp : Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        container = HeureuxAppContainer(context = this)
        userPreferencesRepository = UserPreferencesRepository(dataStore = user_dataStore)
    }
}