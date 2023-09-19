package com.example.productapp.product.repository

import com.example.productapp.base.model.ResponseModel
import com.example.productapp.product.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepositoryHelper {

    /**
     * Remote
     */
    suspend fun getProducts(search: String? = null): Flow<ResponseModel<List<Product>>>
    suspend fun getProduct(id: Int): Flow<Product>

    /**
     * Local
     */
    suspend fun getSavedProducts(): Flow<List<Product>>
    suspend fun getSavedProduct(id: Int): Flow<Product>
    suspend fun save(product: Product): Flow<Long>
    suspend fun remove(product: Product): Flow<Int>
}
