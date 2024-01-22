package com.heureux.properties.data

import android.content.Context
import com.heureux.properties.data.repositories.HeureuxProfileRepository
import com.heureux.properties.data.repositories.HeureuxPropertiesRepository
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.repositories.PropertiesRepository
import com.heureux.properties.data.sources.HeureuxProfileDataSource
import com.heureux.properties.data.sources.HeureuxPropertiesDataSource

/**
 * An interface that provides access to the profile and properties repositories.
 */
interface AppContainer {
    val profileRepository: ProfileRepository
    val propertiesRepository: PropertiesRepository
}

/**
 * An implementation of the AppContainer interface that uses the Heureux data sources.
 */
class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())

    override val propertiesRepository: PropertiesRepository =
        HeureuxPropertiesRepository(dataSource = HeureuxPropertiesDataSource())
}
