package com.example.productapp.product.repository.local

import com.example.productapp.product.model.Product

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */
interface ProductLocalDataSource {
    suspend fun getSavedProducts(): List<Product>
    suspend fun getSavedProduct(id: Int): Product
    suspend fun save(product: Product): Long
    suspend fun remove(product: Product): Int
}