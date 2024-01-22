package com.heureux.admin

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.heureux.admin.data.AppContainer
import com.heureux.admin.data.HeureuxAppContainer
import com.heureux.admin.data.repositories.UserPreferencesRepository

/**
 * The name of the user preference file.
 */
private const val USER_PREFERENCE_NAME = "admin_preferences"

/**
 * A delegate for accessing the user preferences data store.
 */
val Context.user_dataStore by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)

/**
 * The main application class.
 */
class HeureuxAdminApp : Application() {

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

        container = HeureuxAppContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore = user_dataStore)
    }
}