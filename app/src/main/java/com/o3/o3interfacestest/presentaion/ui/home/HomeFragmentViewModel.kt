package com.o3.o3interfacestest.presentaion.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.o3.o3interfacestest.common.Response
import com.o3.o3interfacestest.domain.mapper.toDomainUsersNullableList
import com.o3.o3interfacestest.domain.mapper.toUserDto
import com.o3.o3interfacestest.domain.mapper.toUserResponseDto
import com.o3.o3interfacestest.domain.model.User
import com.o3.o3interfacestest.domain.model.UserResponse
import com.o3.o3interfacestest.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private var dataRepoImpl: DataRepository,
    private var savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val USER_NAME = "USERNAMES"
    }

    var currUserName: String = ""
        set(value) {
            savedStateHandle.set(USER_NAME, value)
            field = value
        }
        get() = savedStateHandle.get<String>(USER_NAME) ?: ""

    val uiUpdates = MutableStateFlow<Response<List<User?>>>(Response.Idle("Ideal State"))

    private var currentQueryValue: UserResponse? = null


    suspend fun searchAge(queryString: UserResponse) {
        currentQueryValue = queryString
        uiUpdates.emit(Response.Loading())
        dataRepoImpl.getSearchResultStream(queryString.toUserResponseDto()).collect {
            viewModelScope.launch {
                ensureActive()
                uiUpdates.emit(Response.Success(it.toDomainUsersNullableList()))
            }
        }
    }

    fun markIdleState() {
        uiUpdates.value = Response.Idle("Ideal")
    }

}