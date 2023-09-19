package com.example.productapp.product.repository.remote

import com.example.productapp.base.model.ResponseModel
import com.example.productapp.product.model.Product

interface ProductRemoteDataSource {
    suspend fun getProducts(search: String? = null): ResponseModel<List<Product>>
    suspend fun getProduct(id: Int): Product
}