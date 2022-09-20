package com.r3d1r4ph.hellouser.app.di

import android.content.Context
import androidx.room.Room
import com.r3d1r4ph.data.common.AppDatabase
import com.r3d1r4ph.data.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.getUserDao()
}