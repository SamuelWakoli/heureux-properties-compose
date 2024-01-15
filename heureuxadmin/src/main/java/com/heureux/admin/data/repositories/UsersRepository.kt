package com.heureux.admin.data.repositories

import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getAllUsers(
        onFailure: (exception: Exception) -> Unit
    ) : Flow<List<HeureuxUser>>
}