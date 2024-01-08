package com.heureux.admin.data.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object {
        val TAG = UserPreferencesRepository::class.java.simpleName
        val THEME = stringPreferencesKey("theme")
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    }

    val getThemeData: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[THEME] ?: "System"
    }

    val getDynamicColor: Flow<Boolean> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DYNAMIC_COLOR] ?: false
    }

    suspend fun saveTheme(themeData: String) {
        dataStore.edit { preferences ->
            preferences[THEME] = themeData
        }
    }

    suspend fun saveDynamicColor(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[DYNAMIC_COLOR] = boolean
        }
    }
}