package com.heureux.admin

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.heureux.admin.data.AppContainer
import com.heureux.admin.data.HeureuxAppContainer
import com.heureux.admin.data.repositories.UserPreferencesRepository

private const val USER_PREFERENCE_NAME = "admin_preferences"
val Context.user_dataStore by preferencesDataStore(
    name = USER_PREFERENCE_NAME
)
class HeureuxAdminApp : Application() {

    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()

        container = HeureuxAppContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore = user_dataStore)
    }
}