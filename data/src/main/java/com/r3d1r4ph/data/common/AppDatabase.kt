package com.r3d1r4ph.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.r3d1r4ph.data.user.UserDao
import com.r3d1r4ph.data.user.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}