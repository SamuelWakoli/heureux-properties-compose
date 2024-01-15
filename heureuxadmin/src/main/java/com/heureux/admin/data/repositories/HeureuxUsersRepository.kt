package com.heureux.admin.data.repositories

import com.heureux.admin.data.sources.UsersDataSource
import com.heureux.admin.data.types.HeureuxUser
import kotlinx.coroutines.flow.Flow

class HeureuxUsersRepository(private val dataSource: UsersDataSource) : UsersRepository {
    override fun getAllUsers(onFailure: (exception: Exception) -> Unit): Flow<List<HeureuxUser>> =
        dataSource.getAllUsers(onFailure)
}