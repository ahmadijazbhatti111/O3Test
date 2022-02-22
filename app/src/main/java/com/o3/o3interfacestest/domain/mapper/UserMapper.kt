package com.o3.o3interfacestest.domain.mapper

import com.o3.o3interfacestest.data.source.model.UserDto
import com.o3.o3interfacestest.domain.model.User

fun UserDto.toUserDomain(): User {
    return User(
        count = count,
        name = name,
        age = age,
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        count = count,
        name = name,
        age = age,
    )
}


fun List<UserDto>.toDomainUsersList(): List<User> {
    val list = mutableListOf<User>()
    forEach {
        list.add(it.toUserDomain())
    }
    return list
}

fun List<UserDto>.toDomainUsersNullableList(): List<User> {
    val list = mutableListOf<User>()
    forEach {
        if(it.age == null){
            it.age = ""
        }
        list.add(it.toUserDomain())
    }
    return list
}