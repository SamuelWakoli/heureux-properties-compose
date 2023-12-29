package com.heureux.properties.data

import android.content.Context
import com.heureux.properties.data.repositories.HeureuxProfileRepository
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.sources.HeureuxProfileDataSource

interface AppContainer {
    val profileRepository: ProfileRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())
}
