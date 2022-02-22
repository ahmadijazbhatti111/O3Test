package com.o3.o3interfacestest.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserDto(
    @ColumnInfo(defaultValue = "0") var age: String = "",
    @PrimaryKey
    val count: Int = 0,
    val name: String = "",
)

