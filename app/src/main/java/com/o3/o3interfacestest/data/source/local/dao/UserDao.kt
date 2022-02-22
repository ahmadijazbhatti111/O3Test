package com.o3.o3interfacestest.data.source.local.dao

import androidx.room.*
import com.o3.o3interfacestest.data.source.model.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUserNameList(): Flow<List<UserDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserName(dataModel: UserDto): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserNames(dataModel: List<UserDto>)

    @Delete
    fun deleteUserName(datamodel: UserDto)

    @Update
    fun updateUser(dataModel: UserDto)

    @Query("SELECT * FROM user WHERE  name like :username")
    fun getUserByName(username: String): List<UserDto>
}
