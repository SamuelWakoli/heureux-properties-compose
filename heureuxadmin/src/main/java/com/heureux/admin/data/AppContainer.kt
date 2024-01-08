package com.heureux.admin.data

import android.content.Context
import com.heureux.admin.data.repositories.HeureuxProfileRepository
import com.heureux.admin.data.repositories.ProfileRepository
import com.heureux.admin.data.sources.HeureuxProfileDataSource

interface AppContainer {
    val profileRepository: ProfileRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())

}
