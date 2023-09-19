package com.example.productapp.product.di

import com.example.productapp.product.repository.ProductRepositoryHelper
import com.example.productapp.product.repository.ProductRepositoryHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {
    @Binds
    abstract fun providesProductRepositoryHelper(productRepository: ProductRepositoryHelperImpl): ProductRepositoryHelper
}