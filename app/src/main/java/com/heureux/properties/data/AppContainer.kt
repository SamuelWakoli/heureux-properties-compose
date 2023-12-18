package com.heureux.properties.data

import android.content.Context

interface AppContainer {
    val userAuthRepository: AuthRepository
    val heureuxFirestoreRepository: FirestoreRepository
}

class HeureuxAppContainer(context: Context) : AppContainer {

    override val userAuthRepository: AuthRepository = UserAuthRepository(context = context)

    override val heureuxFirestoreRepository: FirestoreRepository =
        HeureuxFirestoreRepository(context = context)
}
