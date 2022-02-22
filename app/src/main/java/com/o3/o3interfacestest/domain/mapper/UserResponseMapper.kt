package com.o3.o3interfacestest.domain.mapper

import com.o3.o3interfacestest.data.source.model.UserResponseDto
import com.o3.o3interfacestest.domain.model.UserResponse

fun UserResponseDto.toUserResponseDomain(): UserResponse {
    return UserResponse(
        userName,count
    )
}

fun UserResponse.toUserResponseDto(): UserResponseDto {
    return UserResponseDto(
        userName,count
    )
}

fun List<UserResponseDto>.toDomainUsersResponseList(): List<UserResponse> {
    val list = mutableListOf<UserResponse>()
    forEach {
        list.add(it.toUserResponseDomain())
    }
    return list
}