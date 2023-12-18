package com.heureux.properties.data

import android.content.Context

interface AppContainer {
    val heureuxFirestoreRepository: FirestoreRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {
    override val heureuxFirestoreRepository: FirestoreRepository =
        HeureuxFirestoreRepository(context = context)
}
