package com.o3.o3interfacestest.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.o3.o3interfacestest.data.source.local.dao.UserDao
import com.o3.o3interfacestest.data.source.model.UserDto

@Database(entities = [UserDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "myDb"
    }
    abstract fun userNameDao(): UserDao
}

