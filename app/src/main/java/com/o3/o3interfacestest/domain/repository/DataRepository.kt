package com.o3.o3interfacestest.domain.repository

import com.o3.o3interfacestest.data.source.model.UserDto
import com.o3.o3interfacestest.data.source.model.UserResponseDto
import kotlinx.coroutines.flow.Flow

interface DataRepository{
    fun getAllDataFromLocalDb(): Flow<List<UserDto>>

    suspend fun getSearchResultStream(
        query: UserResponseDto
    ): Flow<List<UserDto>>

    fun delDataFromRoom(body: UserDto)

    fun updateUser(dataModel: UserDto)
}

