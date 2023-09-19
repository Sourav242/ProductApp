package com.example.productapp.product.di

import com.example.productapp.product.repository.local.ProductLocalDataSource
import com.example.productapp.product.repository.local.ProductLocalDataSourceImpl
import com.example.productapp.product.repository.remote.ProductRemoteDataSource
import com.example.productapp.product.repository.remote.ProductRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindLocalDataSource(impl: ProductLocalDataSourceImpl): ProductLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(impl: ProductRemoteDataSourceImpl): ProductRemoteDataSource
}