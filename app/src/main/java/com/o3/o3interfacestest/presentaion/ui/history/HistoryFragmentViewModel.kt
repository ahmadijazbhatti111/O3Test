package com.o3.o3interfacestest.presentaion.ui.history

import androidx.lifecycle.ViewModel
import com.o3.o3interfacestest.common.Response
import com.o3.o3interfacestest.domain.mapper.toDomainUsersList
import com.o3.o3interfacestest.domain.mapper.toUserDto
import com.o3.o3interfacestest.domain.model.User
import com.o3.o3interfacestest.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(private var dataRepoImpl: DataRepository) : ViewModel() {
    val uiUpdates = MutableStateFlow<Response<List<User?>>>(Response.Idle("Ideal State"))

    suspend fun getDataFromLocalDb() {
        uiUpdates.emit(Response.Loading())
        dataRepoImpl.getAllDataFromLocalDb().collect {

            uiUpdates.emit(Response.Success(it.toDomainUsersList()))
        }
    }
    fun deleteUser(userData: User){
        dataRepoImpl.delDataFromRoom(userData.toUserDto())
    }
}