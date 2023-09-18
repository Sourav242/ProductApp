package com.example.productapp.product.di

import com.example.productapp.product.api.ProductApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

@Module
@InstallIn(SingletonComponent::class)
class ProductNetworkModule {
    @Singleton
    @Provides
    fun providesProductApiInterface(retrofit: Retrofit): ProductApiInterface {
        return retrofit.create(ProductApiInterface::class.java)
    }
}