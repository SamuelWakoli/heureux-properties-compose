package com.heureux.properties

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.heureux.properties.data.AppContainer
import com.heureux.properties.data.HeureuxAppContainer
import com.heureux.properties.data.repositories.UserPreferencesRepository

/**
 * The name of the user preferences file.
 */
private const val USER_PREFERENCE_NAME = "user_preferences"

/**
 * Creates a Preferences DataStore for user preferences.
 */
val Context.user_dataStore by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

/**
 * The main application class.
 */
class HeureuxApp : Application() {
    /**
     * The application container.
     */
    lateinit var container: AppContainer

    /**
     * The user preferences repository.
     */
    lateinit var userPreferencesRepository: UserPreferencesRepository

    /**
     * Called when the application is created.
     */
    override fun onCreate() {
        super.onCreate()

        container = HeureuxAppContainer(context = this)
        userPreferencesRepository = UserPreferencesRepository(dataStore = user_dataStore)
    }
}