package com.heureux.properties.data

import android.content.Context
import com.heureux.properties.data.repositories.FirestoreRepository
import com.heureux.properties.data.repositories.HeureuxFirestoreRepository

interface AppContainer {
    val heureuxFirestoreRepository: FirestoreRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {
    override val heureuxFirestoreRepository: FirestoreRepository =
        HeureuxFirestoreRepository(context = context)
}
