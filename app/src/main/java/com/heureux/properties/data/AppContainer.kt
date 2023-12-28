package com.heureux.properties.data

import android.content.Context
import com.heureux.properties.data.repositories.FirestoreRepository
import com.heureux.properties.data.repositories.HeureuxFirestoreRepository
import com.heureux.properties.data.repositories.HeureuxProfileRepository
import com.heureux.properties.data.repositories.ProfileRepository
import com.heureux.properties.data.sources.HeureuxProfileDataSource

interface AppContainer {
    val profileRepository: ProfileRepository
    val heureuxFirestoreRepository: FirestoreRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())

    override val heureuxFirestoreRepository: FirestoreRepository =
        HeureuxFirestoreRepository(context = context)
}
