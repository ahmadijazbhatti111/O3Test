package com.o3.o3interfacestest.data.repository

import com.o3.o3interfacestest.common.DataSourceTypes
import com.o3.o3interfacestest.data.source.model.UserDto
import com.o3.o3interfacestest.data.source.model.UserResponseDto
import com.o3.o3interfacestest.di.DataSources
import com.o3.o3interfacestest.domain.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepositoryImpl(private var networkModel: DataSources) : DataRepository {
    private fun identifySourceOfDATA(): DataSourceTypes {
        return DataSourceTypes.FROM_NETWORK
    }

    override fun getAllDataFromLocalDb(): Flow<List<UserDto>> {
        return networkModel.sourceOfTruthLocalDB().userNameDao().getUserNameList()

    }

    override fun delDataFromRoom(body: UserDto) {
        networkModel.sourceOfTruthLocalDB().userNameDao().deleteUserName(body)
    }

    override fun updateUser(dataModel: UserDto) {
        networkModel.sourceOfTruthLocalDB().userNameDao().updateUser(dataModel)
    }

    override suspend fun getSearchResultStream(
        query: UserResponseDto
    ): Flow<List<UserDto>> {
        return when (identifySourceOfDATA()) {
            DataSourceTypes.FROM_NETWORK -> getData(query)
            DataSourceTypes.FROM_LOCALDB -> TODO()
        }
    }

    private suspend fun getData(
        query: UserResponseDto
    ): Flow<List<UserDto>> {
        return flow {
            emit(hitApiOrDbSingleUser(query))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun hitApiOrDbSingleUser(query: UserResponseDto): List<UserDto> {
        val listFromLocalDb = doesUserExistInLocalDB(query)
        return listFromLocalDb.ifEmpty {
            try {
                val response = getDataFromNetworkSingleUser(query)
                sendDataToRoom(listOf(response!!))
                (listOf(response))
            } catch (e: Exception) {
                (emptyList())
            }
        }
    }

    private suspend fun getDataFromNetworkSingleUser(query: UserResponseDto): UserDto? {
        return networkModel.sourceOfTruthNetworkDB()
            .getSingleNameAge(query.userName).body()
    }

    private fun sendDataToRoom(body: List<UserDto>) {
        networkModel.sourceOfTruthLocalDB().userNameDao().insertUserNames(body)
    }

    private fun doesUserExistInLocalDB(userDataDto: UserResponseDto): List<UserDto> {
        return networkModel.sourceOfTruthLocalDB()
            .userNameDao()
            .getUserByName(userDataDto.userName)
    }
}