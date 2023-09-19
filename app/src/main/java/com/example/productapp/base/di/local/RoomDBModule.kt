package com.example.productapp.base.di.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {
    @Singleton
    @Provides
    fun providesRoomDB(@ApplicationContext context: Context): ProductDatabase =
        Room.databaseBuilder(
            context,
            ProductDatabase::class.java, "product"
        ).build()
}