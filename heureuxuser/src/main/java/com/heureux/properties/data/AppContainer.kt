package com.heureux.properties.data

import android.content.Context
import com.heureux.properties.data.repositories.HeureuxProfileRepository
import com.heureux.properties.data.repositories.HeureuxPropertiesRepository
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.repositories.PropertiesRepository
import com.heureux.properties.data.sources.HeureuxProfileDataSource
import com.heureux.properties.data.sources.HeureuxPropertiesDataSource

interface AppContainer {
    val profileRepository: ProfileRepository
    val propertiesRepository: PropertiesRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())

    override val propertiesRepository: PropertiesRepository =
        HeureuxPropertiesRepository(dataSource = HeureuxPropertiesDataSource())
}
