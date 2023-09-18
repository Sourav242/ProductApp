package com.example.productapp.base.di

import com.example.productapp.ProductApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */

@Module
@InstallIn(SingletonComponent::class)
class ProductApplicationModule {

    companion object {
        lateinit var application: ProductApplication
    }

    @Singleton
    @Provides
    fun getContext(): ProductApplication {
        return application
    }
}