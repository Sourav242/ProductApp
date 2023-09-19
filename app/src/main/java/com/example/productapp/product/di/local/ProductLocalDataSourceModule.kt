package com.example.productapp.product.di.local

import com.example.productapp.base.di.local.ProductDatabase
import com.example.productapp.product.repository.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductLocalDataSourceModule {
    @Singleton
    @Provides
    fun providesProductDao(appDatabase: ProductDatabase): ProductDao {
        return appDatabase.productDao()
    }
}